package com.miriga.rieng.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.miriga.rieng.R
import com.miriga.rieng.adapters.HelpCenterAdapter
import com.miriga.rieng.databinding.FragmentHelpCenterBinding
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast

class HelpCenterFragment : Fragment() {

    private lateinit var helpCenterViewModel: HelpCenterViewModel
    private lateinit var binding:FragmentHelpCenterBinding
    private val helpCenterAdapter: HelpCenterAdapter by lazy { HelpCenterAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideHelpCenterViewModelFactory()
        helpCenterViewModel =
            ViewModelProviders.of(this,factory).get(HelpCenterViewModel::class.java)
        helpCenterViewModel.fetchHelpCenters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpCenterBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = helpCenterViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.refresher.setOnRefreshListener {
            helpCenterViewModel.fetchHelpCenters()
            binding.refresher.isRefreshing = false
        }
        binding.recyclerView.adapter = helpCenterAdapter
        val itemDecorator  = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.divider)!!)

        binding.recyclerView.addItemDecoration( DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)
        )

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        helpCenterViewModel.getData().observe(viewLifecycleOwner, Observer {
            helpCenterAdapter.submitList(it)
        })

        helpCenterViewModel.getLoader().observe(viewLifecycleOwner, Observer {
            binding.refresher.isRefreshing = it
        })

        helpCenterViewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }
}