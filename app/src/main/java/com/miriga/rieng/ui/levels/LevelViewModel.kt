package com.miriga.rieng.ui.levels

import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.LevelRepository

class LevelViewModel internal constructor(
    private val levelRepository: LevelRepository
) : ViewModel() {

    fun getData() = levelRepository.getData()

    fun fetchLevel() = levelRepository.fetchLevel()

    fun getErrors() = levelRepository.getErrors()

    fun getLoader() = levelRepository.getLoader()
}