package com.miriga.rieng.ui.subtopics

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
import com.miriga.rieng.adapters.SubTopicAdapter
import com.miriga.rieng.databinding.FragmentSubtopicBinding
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast

class SubTopicFragment : Fragment() {

    private lateinit var subTopicViewModel: SubTopicViewModel
    private val topicAdapter: SubTopicAdapter by lazy { SubTopicAdapter() }
    private lateinit var binding: FragmentSubtopicBinding
    private var categoryId  = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideSubTopicViewModelFactory()
        subTopicViewModel = ViewModelProviders.of(this,factory).get(SubTopicViewModel::class.java)
        categoryId = SubTopicFragmentArgs.fromBundle(arguments!!).id
        subTopicViewModel.fetchTopics(categoryId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubtopicBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel = subTopicViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        binding.refresher.setOnRefreshListener {
            subTopicViewModel.fetchTopics(categoryId)
            binding.refresher.isRefreshing = false
        }
        binding.recyclerView.adapter = topicAdapter
        val itemDecorator  = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        binding.recyclerView.addItemDecoration( DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subTopicViewModel.getData().observe(viewLifecycleOwner, Observer { result ->
            binding.hasItems = (result != null && result.isNotEmpty())
            topicAdapter.submitList(result)
        })
        subTopicViewModel.getLoader().observe(viewLifecycleOwner, Observer {
            binding.refresher.isRefreshing = it
        })
        subTopicViewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }

}