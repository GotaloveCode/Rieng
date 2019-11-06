package com.miriga.rieng.ui.article

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.Article

class ArticleItemViewModel(
    item: Article
) : ViewModel() {
    val description = ObservableField<String>(item.article_content)
    val title = ObservableField<String>(item.article_title)
    val excerpt = ObservableField<String>(item.article_content.take(210)+ " ...")
}