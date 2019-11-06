package com.miriga.rieng.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miriga.rieng.data.repos.AboutRepository

class AboutViewModelFactory(private val aboutRepository: AboutRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AboutViewModel(aboutRepository) as T
    }
}
