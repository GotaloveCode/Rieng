package com.miriga.rieng.ui.levels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miriga.rieng.data.repos.LevelRepository

class LevelViewModelFactory(private val levelRepository: LevelRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LevelViewModel(levelRepository) as T
    }
}