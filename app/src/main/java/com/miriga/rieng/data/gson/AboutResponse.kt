package com.miriga.rieng.data.gson

data class AboutResponse(
    val `data`: List<About>
)

data class About(
    val article_content: String,
    val title: String,
    val id: Int
)