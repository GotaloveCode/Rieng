package com.miriga.rieng.ui.levels


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration

import com.miriga.rieng.R
import com.miriga.rieng.adapters.LevelAdapter
import com.miriga.rieng.databinding.FragmentLevelBinding
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast

class LevelFragment : Fragment() {
    private lateinit var levelViewModel: LevelViewModel
    private val levelAdapter: LevelAdapter by lazy { LevelAdapter() }
    private lateinit var binding: FragmentLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideLevelViewModelFactory()
        levelViewModel = ViewModelProviders.of(this, factory).get(LevelViewModel::class.java)
        levelViewModel.fetchLevel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLevelBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = levelViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        binding.refresher.setOnRefreshListener {
            levelViewModel.fetchLevel()
            binding.refresher.isRefreshing = false
        }
        binding.recyclerView.adapter = levelAdapter
        val itemDecorator = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        levelViewModel.getData().observe(viewLifecycleOwner, Observer { result ->
            binding.hasItems = (result != null && result.isNotEmpty())
            levelAdapter.submitList(result)
        })
        levelViewModel.getLoader().observe(viewLifecycleOwner, Observer {
            binding.refresher.isRefreshing = it
        })
        levelViewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }


}
