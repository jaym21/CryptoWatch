package dev.jaym21.cryptoapi.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.cryptoapi.models.entities.Data

@JsonClass(generateAdapter = true)
data class HistoricalResponse(
    @Json(name = "Aggregated")
    val aggregated: Boolean?,
    @Json(name = "Data")
    val `data`: List<Data>?,
    @Json(name = "TimeFrom")
    val timeFrom: Int?,
    @Json(name = "TimeTo")
    val timeTo: Int?
)