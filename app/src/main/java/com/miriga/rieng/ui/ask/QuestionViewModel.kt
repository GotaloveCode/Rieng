package com.miriga.rieng.ui.ask

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miriga.rieng.R
import com.miriga.rieng.data.gson.PostQuestionResponse
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import com.miriga.rieng.utils.isConnectedToNetwork
import retrofit2.Call
import retrofit2.Callback

class QuestionViewModel internal constructor(
    private val context: Context
) : ViewModel() {
    private lateinit var que: QuestionField
    private var onFocusQuestion: View.OnFocusChangeListener? = null
    private var mDataApi  = MutableLiveData<PostQuestionResponse>()
    private var mError = MutableLiveData<String>()
    private var apiService = ServiceBuilder.buildService(ApiService::class.java)
    private var _loader = MutableLiveData<Boolean>()

    fun init() {
        que = QuestionField()
        onFocusQuestion = View.OnFocusChangeListener { view, focused ->
            val et = view as EditText
            if (et.text.isNotEmpty() && !focused) {
                que.isQuestionValid(true)
            }
        }
    }


    fun getQuestion(): QuestionField? {
        return que
    }

    fun getQuestionOnFocusChangeListener(): View.OnFocusChangeListener? {
        return onFocusQuestion
    }
    

    fun onQuestionClick() {
        que.isQuestionValid(true)
        if (!context.isConnectedToNetwork()) {
            mError.value = context.getString(R.string.no_internet_connection)
        } else {
            if (que.isValid()) {
                doQuestion(que.question)
            }
        }
    }

    fun getData() =  mDataApi as LiveData<PostQuestionResponse>

    fun getErrorUpdates() = mError as LiveData<String>

    fun getLoader() = _loader as LiveData<Boolean>


    private fun doQuestion(_question: String) {
        _loader.value = true

        val call: Call<PostQuestionResponse> = apiService.postquestion( _question)

        call.enqueue(object : Callback<PostQuestionResponse> {
            override fun onResponse(call: Call<PostQuestionResponse>, response: retrofit2.Response<PostQuestionResponse>) {
                _loader.value = false
                if(response.code() == 201){
                    val data = response.body()!!
                    mDataApi.value = data
                    mError.value = "Your question has been posted successfully"
                }
            }
            override fun onFailure(call: Call<PostQuestionResponse>?, t: Throwable?) {
                _loader.value = false
                mError.value = t?.message
            }
        })
    }

}
