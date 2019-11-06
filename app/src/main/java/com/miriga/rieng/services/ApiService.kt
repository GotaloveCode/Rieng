package com.miriga.rieng.services

import com.miriga.rieng.data.gson.*
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST


/**
 * API communication setup
 */
interface ApiService {
    
    @FormUrlEncoded
    @POST("postuser")
    fun postuser(@Field("gender") gender: String, @Field("age") age: String ): Call<PostUserResponse>

    @FormUrlEncoded
    @POST("updateuser")
    fun updateuser(@Field("gender") gender: String, @Field("age") age: String,@Field("id") id: Int ): Call<PostUserResponse>

    @FormUrlEncoded
    @POST("postquestion")
    fun postquestion(@Field("question_content") question_content: String): Call<PostQuestionResponse>

    @GET("listcategories")
    fun topics(): Call<CategoriesResponse>

    @GET("childcategories/{id}")
    fun subtopics(@Path(value = "id", encoded = true) id: Int): Call<ChildCategoryResponse>

    @GET("articlelist/{id}")
    fun articles(@Path(value = "id", encoded = true) id: Int): Call<ArticlesResponse>

    @GET("listhelpcenters")
    fun helpcenters(): Call<HelpCenterResponse>

    @GET("listcontents")
    fun about(): Call<AboutResponse>

    @GET("quizzquestions/{id}")
    fun quiz(@Path(value = "id", encoded = true) id: Int): Call<QuestionReponse>

    @GET("listquizzlevels")
    fun levels(): Call<LevelResponse>
}