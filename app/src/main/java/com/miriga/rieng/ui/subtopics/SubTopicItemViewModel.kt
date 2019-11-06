package com.miriga.rieng.ui.subtopics

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.ChildCategory
import com.miriga.rieng.utils.Constants

class SubTopicItemViewModel(
    item: ChildCategory
) : ViewModel() {
    val title = ObservableField<String>(item.category)
    val imageUrl = ObservableField<String>(Constants.IMAGE_URL +item.image)
}