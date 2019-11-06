

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.Category
import com.miriga.rieng.data.gson.CategoriesResponse
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class TopicRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val categories =  MutableLiveData<List<Category>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = categories as LiveData<List<Category>>

   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchTopics(){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<CategoriesResponse> = apiService.topics()
        call.enqueue(object : Callback<CategoriesResponse> {
            override fun onResponse(call: Call<CategoriesResponse>, response: retrofit2.Response<CategoriesResponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{ d ->
                    categories.value = d
                }
            }
            override fun onFailure(call: Call<CategoriesResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any topics")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TopicRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: TopicRepository().also { instance = it }
            }
    }
    
}