package com.miriga.rieng.utils

import android.content.Context
import com.miriga.rieng.data.gson.Article
import com.miriga.rieng.data.repos.*
import com.miriga.rieng.ui.about.AboutViewModelFactory
import com.miriga.rieng.ui.article.ArticlesViewModelFactory
import com.miriga.rieng.ui.articledetail.ArticleDetailViewModelFactory
import com.miriga.rieng.ui.ask.QuestionViewModelFactory
import com.miriga.rieng.ui.help.HelpCenterViewModelFactory
import com.miriga.rieng.ui.levels.LevelViewModelFactory
import com.miriga.rieng.ui.login.LoginViewModelFactory
import com.miriga.rieng.ui.quiz.QuizViewModelFactory
import com.miriga.rieng.ui.subtopics.SubTopicViewModelFactory
import com.miriga.rieng.ui.topics.TopicsViewModelFactory

object InjectorUtils {


    fun provideLoginViewModelFactory(context: Context): LoginViewModelFactory {
        return LoginViewModelFactory(context)
    }

    fun provideQuestionViewModelFactory(context: Context): QuestionViewModelFactory {
        return QuestionViewModelFactory(context)
    }

    fun provideAboutViewModelFactory(): AboutViewModelFactory {
        val repository = getAboutRepository()
        return AboutViewModelFactory(repository)
    }

    private fun getAboutRepository(): AboutRepository {
        return AboutRepository.getInstance()
    }

    fun provideTopicsViewModelFactory(): TopicsViewModelFactory {
        val repository = getTopicRepository()
        return TopicsViewModelFactory(repository)
    }

    private fun getTopicRepository(): TopicRepository {
        return TopicRepository.getInstance()
    }

    fun provideSubTopicViewModelFactory(): SubTopicViewModelFactory {
        val repository = getSubTopicRepository()
        return SubTopicViewModelFactory(repository)
    }

    private fun getSubTopicRepository(): SubTopicRepository {
        return SubTopicRepository.getInstance()
    }

    fun provideArticlesViewModelFactory(): ArticlesViewModelFactory {
        val repository = getArticleRepository()
        return ArticlesViewModelFactory(repository)
    }

    private fun getArticleRepository(): ArticleRepository {
        return ArticleRepository.getInstance()
    }

    fun provideHelpCenterViewModelFactory(): HelpCenterViewModelFactory {
        val repository = getHelpCenterRepository()
        return HelpCenterViewModelFactory(repository)
    }

    private fun getHelpCenterRepository(): HelpCenterRepository {
        return HelpCenterRepository.getInstance()
    }

    fun provideArticleDetailViewModelFactory(article: Article): ArticleDetailViewModelFactory {
        return ArticleDetailViewModelFactory(article)
    }

    fun provideLevelViewModelFactory(): LevelViewModelFactory {
        val repository = getLevelRepository()
        return LevelViewModelFactory(repository)
    }

    private fun getLevelRepository(): LevelRepository {
        return LevelRepository.getInstance()
    }

    fun provideQuizViewModelFactory(): QuizViewModelFactory {
        val repository = getQuizRepository()
        return QuizViewModelFactory(repository)
    }

    private fun getQuizRepository(): QuizRepository {
        return QuizRepository.getInstance()
    }

}