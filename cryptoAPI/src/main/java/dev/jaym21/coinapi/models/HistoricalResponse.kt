package dev.jaym21.coinapi.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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