package com.miriga.rieng.ui.subtopics

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.SubTopicRepository

class SubTopicViewModel internal constructor(
    private val topicRepository: SubTopicRepository
) : ViewModel() {
    val hasItems: LiveData<Boolean>
    init{
       val topics = getData()
        hasItems = Transformations.map(topics) { it != null }
    }

    fun getData() = topicRepository.getData()

    fun fetchTopics(id:Int) = topicRepository.fetchTopics(id)

    fun getErrors() = topicRepository.getErrors()

    fun getLoader() = topicRepository.getLoader()

}