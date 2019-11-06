package com.miriga.rieng.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miriga.rieng.BuildConfig
import com.miriga.rieng.adapters.AboutAdapter
import com.miriga.rieng.databinding.FragmentAboutBinding
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast

class AboutFragment : BaseFragment() {

    private lateinit var aboutViewModel: AboutViewModel
    private lateinit var binding: FragmentAboutBinding
    private val aboutAdapter: AboutAdapter by lazy { AboutAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideAboutViewModelFactory()
        aboutViewModel =
            ViewModelProviders.of(this,factory).get(AboutViewModel::class.java)
        aboutViewModel.fetchAbout()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = aboutAdapter
        val versionName: String = BuildConfig.VERSION_NAME
        aboutViewModel.setVersion(versionName)
        binding.apply {
            viewModel = aboutViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        aboutViewModel.getData().observe(viewLifecycleOwner, Observer {
            aboutAdapter.submitList(it)
        })

        aboutViewModel.getLoader().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })

        aboutViewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }
}