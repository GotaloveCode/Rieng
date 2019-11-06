package com.miriga.rieng.ui


import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.miriga.rieng.R
import com.miriga.rieng.utils.PreferenceHelper


open class BaseFragment : Fragment() {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceHelper.defaultPrefs(requireContext())
    }


    @VisibleForTesting
    val progressDialog by lazy {
        ProgressDialog(requireContext())
    }

    fun showProgressDialog() {
        Log.d("showProgressDialog","now")
        progressDialog.setMessage(getString(R.string.loading))
        progressDialog.isIndeterminate = true
        if(!progressDialog.isShowing)
            progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }


    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }


    override fun onPause() {
        super.onPause()
        hideProgressDialog()
    }

}
