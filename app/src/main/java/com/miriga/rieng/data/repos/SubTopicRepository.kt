

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.ChildCategory
import com.miriga.rieng.data.gson.ChildCategoryResponse
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class SubTopicRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val categories =  MutableLiveData<List<ChildCategory>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = categories as LiveData<List<ChildCategory>>

   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchTopics(id:Int){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<ChildCategoryResponse> = apiService.subtopics(id)
        call.enqueue(object : Callback<ChildCategoryResponse> {
            override fun onResponse(call: Call<ChildCategoryResponse>, response: retrofit2.Response<ChildCategoryResponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{ d ->
                    categories.value = d
                }
            }
            override fun onFailure(call: Call<ChildCategoryResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any subtopics")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: SubTopicRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SubTopicRepository().also { instance = it }
            }
    }
    
}