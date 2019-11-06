package com.miriga.rieng.ui.subtopics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miriga.rieng.data.repos.SubTopicRepository

class SubTopicViewModelFactory (private val subTopicRepository: SubTopicRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SubTopicViewModel(subTopicRepository) as T
    }
}
