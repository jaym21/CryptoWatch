package dev.jaym21.cryptoapi.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.cryptoapi.models.entities.SourceInfo

@JsonClass(generateAdapter = true)
data class NewsData(
    @Json(name = "body")
    val body: String?,
    @Json(name = "categories")
    val categories: String?,
    @Json(name = "downvotes")
    val downvotes: String?,
    @Json(name = "guid")
    val guid: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "imageurl")
    val imageurl: String?,
    @Json(name = "lang")
    val lang: String?,
    @Json(name = "published_on")
    val publishedOn: Int?,
    @Json(name = "source")
    val source: String?,
    @Json(name = "source_info")
    val sourceInfo: SourceInfo?,
    @Json(name = "tags")
    val tags: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "upvotes")
    val upvotes: String?,
    @Json(name = "url")
    val url: String?
)