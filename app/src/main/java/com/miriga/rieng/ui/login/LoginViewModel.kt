package com.miriga.rieng.ui.login

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miriga.rieng.R
import com.miriga.rieng.data.gson.PostUserResponse
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import com.miriga.rieng.utils.isConnectedToNetwork
import retrofit2.Call
import retrofit2.Callback


class LoginViewModel internal constructor(
    private val context: Context
) : ViewModel() {
    private lateinit var login: LoginFields
    private var onFocusAge: View.OnFocusChangeListener? = null
    private var mDataApi  = MutableLiveData<PostUserResponse>()
    private var mError = MutableLiveData<String>()
    private var apiService = ServiceBuilder.buildService(ApiService::class.java)
    private var _loader = MutableLiveData<Boolean>()

    fun init() {
        login = LoginFields()
        onFocusAge = View.OnFocusChangeListener { view, focused ->
            val et = view as EditText
            if (et.text.isNotEmpty() && !focused) {
                login.isAgeValid(true)
            }
        }
    }


    fun getLogin(): LoginFields? {
        return login
    }

    fun getAgeOnFocusChangeListener(): View.OnFocusChangeListener? {
        return onFocusAge
    }


    fun onSelectGenderItem(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        login.gender = parent.selectedItem.toString()

    }

    fun onLoginClick() {
        login.isAgeValid(true)
        login.isGenderValid(true)
        if (!context.isConnectedToNetwork()) {
            mError.value = context.getString(R.string.no_internet_connection)
        } else {
            if (login.isValid()) {
                doLogin(login.age, login.gender)
            }else{
                mError.value = context.getString(R.string.error_field_required)
            }
        }
    }

    fun getData() =  mDataApi as LiveData<PostUserResponse>

    fun getErrorUpdates() = mError as LiveData<String>

    fun getLoader() = _loader as LiveData<Boolean>


    private fun doLogin(age: String, gender: String) {
        _loader.value = true

        val call: Call<PostUserResponse> = apiService.postuser(age=age,gender = gender)

        call.enqueue(object : Callback<PostUserResponse> {
            override fun onResponse(call: Call<PostUserResponse>, response: retrofit2.Response<PostUserResponse>) {
                _loader.value = false
                if(response.code() == 201){
                    val data = response.body()!!
                    mDataApi.value = data
                }
            }
            override fun onFailure(call: Call<PostUserResponse>?, t: Throwable?) {
                _loader.value = false
                mError.value = t?.message
            }
        })
    }

}


