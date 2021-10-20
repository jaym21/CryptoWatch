package dev.jaym21.cryptoapi.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsSourceInfo(
    @Json(name = "img")
    val img: String?,
    @Json(name = "lang")
    val lang: String?,
    @Json(name = "name")
    val name: String?
)