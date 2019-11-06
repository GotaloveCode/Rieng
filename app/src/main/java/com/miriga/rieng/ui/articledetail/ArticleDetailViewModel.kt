package com.miriga.rieng.ui.articledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.Article

class ArticleDetailViewModel internal constructor(
    private val article: Article
) : ViewModel() {

    private val _title = MutableLiveData<String>().apply { value = article.article_title }
    val title: LiveData<String> = _title

    private val _content = MutableLiveData<String>().apply { value = article.article_content }
    val content: LiveData<String> = _content

}