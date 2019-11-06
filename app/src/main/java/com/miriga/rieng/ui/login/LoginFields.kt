package com.miriga.rieng.ui.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.miriga.rieng.BR
import com.miriga.rieng.R
import com.miriga.rieng.utils.Helpers


class LoginFields: BaseObservable() {

    var ageError = ObservableField<Int?>()
    var genderError = ObservableField<Int?>()
    var age: String = ""
        // setter
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }
    var gender: String = ""
        // setter
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    fun isGenderValid(setMessage: Boolean): Boolean {
        return if (gender.isNotEmpty()) {
            genderError.set(null)
            true
        } else {
            if (setMessage)
                genderError.set(R.string.error_field_required)
            false
        }

    }
    fun isAgeValid(setMessage: Boolean): Boolean {
        val numeric = Helpers.isAgeValid(age)

        return if (numeric && age.toInt() > 0 && age.toInt() < 120) {
            ageError.set(null)
            true
        } else {
            if (setMessage)
                ageError.set(R.string.invalid_age)
            false
        }
    }


    @Bindable
    fun isValid(): Boolean {
        var valid = isAgeValid(false)
        valid = isGenderValid(false) && valid
        return valid
    }
}