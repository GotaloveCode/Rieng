package com.miriga.rieng.ui.settings


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import com.miriga.rieng.R
import com.miriga.rieng.data.gson.PostUserResponse
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.Constants
import com.miriga.rieng.utils.Helpers
import com.miriga.rieng.utils.PreferenceHelper.get
import com.miriga.rieng.utils.PreferenceHelper.set
import com.miriga.rieng.utils.showToast
import retrofit2.Call
import retrofit2.Callback


class SettingsFragment : BaseFragment() {
    private var ageString: String? = null
    private var genderString: String? = null
    private var apiService = ServiceBuilder.buildService(ApiService::class.java)
    private lateinit var age:TextView
    private lateinit var gender:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ageString = prefs[Constants.age]
        genderString = prefs[Constants.gender]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        age = root.findViewById(R.id.age)
        gender = root.findViewById(R.id.gender)
        val btnEdit = root.findViewById<Button>(R.id.btnEdit)
        ageString?.let { age.text = it }
        genderString?.let { gender.text = it }
        btnEdit.setOnClickListener { openEditDialog() }

        return root
    }

    private fun openEditDialog() {
        val dialog = Dialog(requireContext())
        dialog.setTitle("Edit Settings")
        dialog.setContentView(R.layout.dialog_edit_settings)
        dialog.setCancelable(true)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val eAge = dialog.findViewById<View>(R.id.age) as EditText
        val eGender = dialog.findViewById<View>(R.id.gender) as AppCompatSpinner
        val adapter = eGender.adapter as ArrayAdapter<String>

        eAge.setText(ageString)
        eGender.setSelection(adapter.getPosition(genderString))

        (dialog.findViewById<View>(R.id.bt_cancel) as AppCompatButton).setOnClickListener { dialog.dismiss() }

        (dialog.findViewById<View>(R.id.bt_submit) as AppCompatButton).setOnClickListener {
            if (!Helpers.isAgeValid(eAge.text.toString())) {
                activity?.showToast(getString(R.string.invalid_age))
            }
            genderString = eGender.selectedItem.toString()
            updateUser(genderString!!, eAge.text.toString())
            dialog.dismiss()
        }

        dialog.show()
        dialog.window!!.attributes = lp
    }

    private fun updateUser(_gender: String, _age: String) {
        showProgressDialog()
        val userId :Int? = prefs[Constants.id]
        val call: Call<PostUserResponse> = apiService.updateuser(_gender, _age, userId?:0)

        call.enqueue(object : Callback<PostUserResponse> {
            override fun onResponse(
                call: Call<PostUserResponse>,
                response: retrofit2.Response<PostUserResponse>
            ) {
                hideProgressDialog()
                if (response.code() == 200) {
                    prefs[Constants.age] = _age
                    prefs[Constants.gender] = _gender
                    age.text = _age
                    gender.text = _gender
                    activity?.showToast("Profile updated successfully")
                }
            }

            override fun onFailure(call: Call<PostUserResponse>?, t: Throwable?) {
                hideProgressDialog()
                activity?.showToast(t?.message?:"Could not update profile")
            }
        })
    }


}
