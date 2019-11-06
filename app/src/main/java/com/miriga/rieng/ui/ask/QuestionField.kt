package com.miriga.rieng.ui.ask

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.miriga.rieng.BR
import com.miriga.rieng.R


class QuestionField: BaseObservable() {
    
    var questionError = ObservableField<Int?>()
   
    var question: String = ""
        // setter
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    fun isQuestionValid(setMessage: Boolean): Boolean {
        return if (question.isNotEmpty()) {
            questionError.set(null)
            true
        } else {
            if (setMessage)
                questionError.set(R.string.error_field_required)
            false
        }

    }

    @Bindable
    fun isValid(): Boolean {
        return isQuestionValid(false)
    }
}