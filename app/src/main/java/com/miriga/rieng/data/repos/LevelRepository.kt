

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.LevelResponse
import com.miriga.rieng.data.gson.Level
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class LevelRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val levels =  MutableLiveData<List<Level>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = levels as LiveData<List<Level>>

   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchLevel(){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<LevelResponse> = apiService.levels()
        call.enqueue(object : Callback<LevelResponse> {
            override fun onResponse(call: Call<LevelResponse>, response: retrofit2.Response<LevelResponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{ d ->
                    levels.value = d
                }
            }
            override fun onFailure(call: Call<LevelResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any levels")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: LevelRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: LevelRepository().also { instance = it }
            }
    }
    
}