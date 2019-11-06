package com.miriga.rieng.ui.help

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miriga.rieng.data.repos.HelpCenterRepository

class HelpCenterViewModelFactory(private val helpCenterRepository: HelpCenterRepository):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HelpCenterViewModel(helpCenterRepository) as T
    }
}