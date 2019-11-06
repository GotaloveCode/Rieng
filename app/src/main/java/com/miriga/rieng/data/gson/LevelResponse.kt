package com.miriga.rieng.data.gson

data class LevelResponse(
    val `data`: List<Level>
)

data class Level(
    val id: Int,
    val level: String
)