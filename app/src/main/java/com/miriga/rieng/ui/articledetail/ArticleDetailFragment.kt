package com.miriga.rieng.ui.articledetail


import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.miriga.rieng.R
import com.miriga.rieng.data.gson.Article
import com.miriga.rieng.databinding.FragmentArticleDetailBinding
import com.miriga.rieng.ui.article.ArticlesFragmentArgs
import com.miriga.rieng.utils.GlideImageGetter
import com.miriga.rieng.utils.InjectorUtils


class ArticleDetailFragment : Fragment() {
    private lateinit var binding: FragmentArticleDetailBinding
    private lateinit var articleDetailViewModel: ArticleDetailViewModel
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        article = ArticleDetailFragmentArgs.fromBundle(arguments!!).article
        val factory = InjectorUtils.provideArticleDetailViewModelFactory(article)
        articleDetailViewModel =
            ViewModelProviders.of(this, factory).get(ArticleDetailViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = articleDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        val imageGetter = GlideImageGetter(requireContext(),binding.description)
        val html = Html.fromHtml(article.article_content, imageGetter, null)
        binding.description.text = html
        return binding.root
    }


}
