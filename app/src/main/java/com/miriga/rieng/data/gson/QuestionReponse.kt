package com.miriga.rieng.data.gson

data class QuestionReponse (val `data`: List<Quiz>)

data class Quiz(
    val id: Int,
    val question: String,
    val quizzlevel_id: String,
    val quizzquestionchoices: List<Choice>
)

data class Choice(
    val answer: String,
    val correct_answer: String?,
    val id: Int,
    val quizzquestion_id: String
)

