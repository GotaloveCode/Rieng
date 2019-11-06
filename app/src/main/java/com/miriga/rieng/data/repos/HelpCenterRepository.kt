

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.HelpCenter
import com.miriga.rieng.data.gson.HelpCenterResponse
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class HelpCenterRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val centers =  MutableLiveData<List<HelpCenter>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = centers as LiveData<List<HelpCenter>>

   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchHelpCenters(){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<HelpCenterResponse> = apiService.helpcenters()
        call.enqueue(object : Callback<HelpCenterResponse> {
            override fun onResponse(call: Call<HelpCenterResponse>, response: retrofit2.Response<HelpCenterResponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{ d ->
                    centers.value = d
                }
            }
            override fun onFailure(call: Call<HelpCenterResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any Help Centers")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: HelpCenterRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: HelpCenterRepository().also { instance = it }
            }
    }
    
}