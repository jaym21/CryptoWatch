package dev.jaym21.cryptoapi.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "Aggregated")
    val aggregated: Boolean?,
    @Json(name = "Data")
    val `data`: List<HistoricalData>?,
    @Json(name = "TimeFrom")
    val timeFrom: Int?,
    @Json(name = "TimeTo")
    val timeTo: Int?
)