

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.Question
import com.miriga.rieng.data.gson.QuestionReponse
import com.miriga.rieng.data.gson.Quiz
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class QuizRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val questions =  MutableLiveData<List<Quiz>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = questions as LiveData<List<Quiz>>
   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchQuiz(id:Int){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<QuestionReponse> = apiService.quiz(id)
        call.enqueue(object : Callback<QuestionReponse> {
            override fun onResponse(call: Call<QuestionReponse>, response: retrofit2.Response<QuestionReponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{  questions.value = it }
            }
            override fun onFailure(call: Call<QuestionReponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any articles")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: QuizRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: QuizRepository().also { instance = it }
            }
    }
    
}