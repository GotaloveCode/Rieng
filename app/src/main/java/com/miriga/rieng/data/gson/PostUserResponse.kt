package com.miriga.rieng.data.gson

data class PostUserResponse(
    val `data`: PostUserData
)

data class PostUserData(
    val age: String,
    val created_at: String,
    val gender: String,
    val id: Int,
    val imei_number: String,
    val updated_at: String
)