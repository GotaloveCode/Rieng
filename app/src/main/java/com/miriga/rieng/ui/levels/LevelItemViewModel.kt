package com.miriga.rieng.ui.levels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.Level

class LevelItemViewModel(
    item: Level
) : ViewModel() {
    val level = ObservableField<String>(item.id.toString())
}