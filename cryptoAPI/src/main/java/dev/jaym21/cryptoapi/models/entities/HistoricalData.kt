package dev.jaym21.cryptoapi.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoricalData(
    @Json(name = "close")
    val close: Double?,
    @Json(name = "conversionSymbol")
    val conversionSymbol: String?,
    @Json(name = "conversionType")
    val conversionType: String?,
    @Json(name = "high")
    val high: Double?,
    @Json(name = "low")
    val low: Double?,
    @Json(name = "open")
    val `open`: Double?,
    @Json(name = "time")
    val time: Int?,
    @Json(name = "volumefrom")
    val volumeFrom: Double?,
    @Json(name = "volumeto")
    val volumeTo: Double?
)