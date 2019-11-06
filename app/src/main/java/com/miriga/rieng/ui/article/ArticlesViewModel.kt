package com.miriga.rieng.ui.article

import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.ArticleRepository

class ArticlesViewModel internal constructor(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun getData() = articleRepository.getData()

    fun fetchArticles(id:Int) = articleRepository.fetchArticles(id)

    fun getErrors() = articleRepository.getErrors()

    fun getLoader() = articleRepository.getLoader()

}