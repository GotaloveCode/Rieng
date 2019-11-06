package com.miriga.rieng.ui.topics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miriga.rieng.adapters.SpacingItemDecoration
import com.miriga.rieng.adapters.TopicAdapter
import com.miriga.rieng.databinding.FragmentTopicsBinding
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.Helpers
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast


class TopicsFragment : BaseFragment() {

    private val topicAdapter: TopicAdapter by lazy { TopicAdapter() }
    private lateinit var viewModel: TopicsViewModel
    private lateinit var binding: FragmentTopicsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideTopicsViewModelFactory()
        viewModel = ViewModelProviders.of(this,factory).get(TopicsViewModel::class.java)
        viewModel.fetchTopics()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopicsBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.refresher.setOnRefreshListener {
            viewModel.fetchTopics()
            binding.refresher.isRefreshing = false
        }


        binding.recyclerView.adapter = topicAdapter

        binding.recyclerView.addItemDecoration(
            SpacingItemDecoration(
                2,
                Helpers.dpToPx(requireContext(), 8),
                true
            )
        )

        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            topicAdapter.submitList(it)
        })

        viewModel.getLoader().observe(viewLifecycleOwner, Observer {
            binding.refresher.isRefreshing = it
        })

        viewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }


}
