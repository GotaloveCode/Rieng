package com.miriga.rieng.ui.topics

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.Category
import com.miriga.rieng.utils.Constants.BASE_URL
import com.miriga.rieng.utils.Constants.IMAGE_URL

class TopicItemViewModel(
    item: Category
) : ViewModel() {
    val description = ObservableField<String>(item.description)
    val category = ObservableField<String>(item.category)
    val imageUrl = ObservableField<String>(IMAGE_URL+item.image)
}