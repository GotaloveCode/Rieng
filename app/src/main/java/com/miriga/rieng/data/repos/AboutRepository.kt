

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.AboutResponse
import com.miriga.rieng.data.gson.About
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class AboutRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val categories =  MutableLiveData<List<About>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = categories as LiveData<List<About>>

   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchAbout(){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<AboutResponse> = apiService.about()
        call.enqueue(object : Callback<AboutResponse> {
            override fun onResponse(call: Call<AboutResponse>, response: retrofit2.Response<AboutResponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{ d ->
                    categories.value = d
                }
            }
            override fun onFailure(call: Call<AboutResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any information")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AboutRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: AboutRepository().also { instance = it }
            }
    }
    
}