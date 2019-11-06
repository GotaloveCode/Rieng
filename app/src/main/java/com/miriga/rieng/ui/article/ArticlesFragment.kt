package com.miriga.rieng.ui.article


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
import com.miriga.rieng.adapters.ArticleAdapter
import com.miriga.rieng.databinding.FragmentArticlesBinding
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast
import kotlinx.android.synthetic.main.list_subtopic_item.*


class ArticlesFragment : Fragment() {

    private lateinit var articlesViewModel: ArticlesViewModel
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter() }
    private lateinit var binding: FragmentArticlesBinding
    private var categoryId = 0
    private lateinit var article:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideArticlesViewModelFactory()
        articlesViewModel = ViewModelProviders.of(this, factory).get(ArticlesViewModel::class.java)
        categoryId = ArticlesFragmentArgs.fromBundle(arguments!!).id
        article = ArticlesFragmentArgs.fromBundle(arguments!!).title
        articleAdapter.setTitle(article)
        articlesViewModel.fetchArticles(categoryId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticlesBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = articlesViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        binding.refresher.setOnRefreshListener {
            articlesViewModel.fetchArticles(categoryId)
            binding.refresher.isRefreshing = false
        }
        binding.recyclerView.adapter = articleAdapter
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

        articlesViewModel.getData().observe(viewLifecycleOwner, Observer { result ->
            binding.hasItems = (result != null && result.isNotEmpty())
            articleAdapter.submitList(result)
        })

        articlesViewModel.getLoader().observe(viewLifecycleOwner, Observer {
            binding.refresher.isRefreshing = it
        })

        articlesViewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }
}
