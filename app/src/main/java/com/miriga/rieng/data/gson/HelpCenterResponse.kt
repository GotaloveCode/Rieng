package com.miriga.rieng.data.gson

data class HelpCenterResponse(
    val `data`: List<HelpCenter>
)
data class HelpCenter (
    val id:Int,
    val name: String,
    val physical_location: String?,
    val contact_number: String?,
    val email:String?,
    val time_closes: String,
    val time_opens:String
)
