package com.miriga.rieng.ui.help

import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.HelpCenterRepository

class HelpCenterViewModel internal constructor(
    private val helpCenterRepository:HelpCenterRepository
) : ViewModel() {

    fun getData() = helpCenterRepository.getData()

    fun fetchHelpCenters() = helpCenterRepository.fetchHelpCenters()

    fun getErrors() = helpCenterRepository.getErrors()

    fun getLoader() = helpCenterRepository.getLoader()

}