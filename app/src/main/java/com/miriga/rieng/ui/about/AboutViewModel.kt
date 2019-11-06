package com.miriga.rieng.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.AboutRepository

class AboutViewModel internal constructor(
    private val aboutRepository: AboutRepository
) : ViewModel() {

    private val _version = MutableLiveData<String>().apply {
        value = "1.0.0"
    }
    val version: LiveData<String> = _version

    fun setVersion(v:String){
        _version.value = v
    }

    fun getData() = aboutRepository.getData()

    fun fetchAbout() = aboutRepository.fetchAbout()

    fun getErrors() = aboutRepository.getErrors()

    fun getLoader() = aboutRepository.getLoader()

}