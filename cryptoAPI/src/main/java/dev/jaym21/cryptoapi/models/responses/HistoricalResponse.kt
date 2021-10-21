package dev.jaym21.cryptoapi.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.cryptoapi.models.entities.Data

@JsonClass(generateAdapter = true)
data class HistoricalResponse(
    @Json(name = "Data")
    val `data`: Data?,
    @Json(name = "HasWarning")
    val hasWarning: Boolean?,
    @Json(name = "Message")
    val message: String?,
    @Json(name = "Response")
    val response: String?,
    @Json(name = "Type")
    val type: Int?
)