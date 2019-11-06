

package com.miriga.rieng.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miriga.rieng.data.gson.ArticlesResponse
import com.miriga.rieng.data.gson.Article
import com.miriga.rieng.services.ApiService
import com.miriga.rieng.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback

class ArticleRepository  {
    private val mError = MutableLiveData<String>()
    private val dataLoader =  MutableLiveData<Boolean>()
    private val categories =  MutableLiveData<List<Article>>()


    fun getLoader()  = dataLoader as LiveData<Boolean>

    fun getData()  = categories as LiveData<List<Article>>

   
    fun setLoader(loading:Boolean){
        dataLoader.value = loading
    }

    fun setError(msg:String){
        mError.value = msg
    }

    fun getErrors()  = mError as LiveData<String>


    fun fetchArticles(id:Int){
        setLoader(true)
        val apiService = ServiceBuilder.buildService(ApiService::class.java)
        val call: Call<ArticlesResponse> = apiService.articles(id)
        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: retrofit2.Response<ArticlesResponse>) {
                setLoader(false)
                val res = response.body()?.data
                res?.let{ d ->
                    categories.value = d
                }
            }
            override fun onFailure(call: Call<ArticlesResponse>?, t: Throwable?) {
                setLoader(false)
                setError("Could not fetch any articles")
            }
        })
    }


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ArticleRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository().also { instance = it }
            }
    }
    
}