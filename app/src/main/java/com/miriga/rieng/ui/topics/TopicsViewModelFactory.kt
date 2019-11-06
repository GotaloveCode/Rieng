package com.miriga.rieng.ui.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miriga.rieng.data.repos.TopicRepository

class TopicsViewModelFactory(private val topicRepository: TopicRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopicsViewModel(topicRepository) as T
    }
}