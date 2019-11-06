package com.miriga.rieng.data.gson


data class Question(
    val id: Int,
    val question: String,
    val quizzlevel_id: String,
    val quizzquestionchoices: List<Answer>
)

data class Answer(
    val answer: String,
    val correct_answer: String?,
    val id: Int,
    val quizzquestion_id: String,
    var selected: Boolean
)