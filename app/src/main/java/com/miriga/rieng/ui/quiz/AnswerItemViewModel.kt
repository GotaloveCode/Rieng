package com.miriga.rieng.ui.quiz

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.gson.Choice

class AnswerItemViewModel(
    item: Choice
) : ViewModel() {
    val level = ObservableField<String>(item.answer)
}