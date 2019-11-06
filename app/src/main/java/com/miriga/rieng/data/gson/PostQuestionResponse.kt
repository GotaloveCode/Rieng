package com.miriga.rieng.data.gson

data class PostQuestionResponse(
    val `data`: PostQuestionData
)

data class PostQuestionData(
    val id: Int,
    val question_content: String
)