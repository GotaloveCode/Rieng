package com.miriga.rieng.utils

import android.content.Context
import android.util.TypedValue


object Helpers {


    fun isAgeValid(age: String): Boolean {
        var numeric = true
        try {
            age.toInt()
        } catch (e: NumberFormatException) {
            numeric = false
        }

        return numeric
    }

    fun dpToPx(c: Context, dp: Int): Int {
        val r = c.resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }
}
