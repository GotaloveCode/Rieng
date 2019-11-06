package com.miriga.rieng.ui.about

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.About

class AboutItemViewModel(
    item: About
) : ViewModel() {
    val description = ObservableField<String>(item.article_content)
    val title = ObservableField<String>(item.title)
}