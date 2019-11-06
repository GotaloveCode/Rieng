package com.miriga.rieng.data.gson

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ArticlesResponse(
    val `data`: List<Article>
)

@Parcelize
data class Article(
    val article_content: String,
    val article_title: String,
    val articlecategory_id: String,
    val created_at: String,
    val id: Int,
    val updated_at: String
) : Parcelable