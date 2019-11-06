package com.miriga.rieng.data.gson

data class CategoriesResponse(
    val `data`: List<Category>
)
data class Category(
    val articlecategory_id: Any,
    val category: String,
    val children_categories: List<ChildCategory>,
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String,
    val updated_at: String
)