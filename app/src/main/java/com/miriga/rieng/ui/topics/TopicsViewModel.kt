package com.miriga.rieng.ui.topics

import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.TopicRepository

class TopicsViewModel internal constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    fun getData() = topicRepository.getData()

    fun fetchTopics() = topicRepository.fetchTopics()

    fun getErrors() = topicRepository.getErrors()

    fun getLoader() = topicRepository.getLoader()
}