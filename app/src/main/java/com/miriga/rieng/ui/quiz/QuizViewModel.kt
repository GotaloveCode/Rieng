package com.miriga.rieng.ui.quiz

import androidx.lifecycle.ViewModel
import com.miriga.rieng.data.repos.QuizRepository

class QuizViewModel internal constructor(private val quizRepository: QuizRepository):ViewModel() {

    fun getData() = quizRepository.getData()

    fun fetchLevel(id:Int) = quizRepository.fetchQuiz(id)

    fun getErrors() = quizRepository.getErrors()

    fun getLoader() = quizRepository.getLoader()
}