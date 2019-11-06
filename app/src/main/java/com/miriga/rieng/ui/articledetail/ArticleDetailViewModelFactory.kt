package com.miriga.rieng.ui.articledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miriga.rieng.data.gson.Article
import com.miriga.rieng.data.repos.ArticleRepository

class ArticleDetailViewModelFactory (private val article: Article) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticleDetailViewModel(article) as T
    }
}
