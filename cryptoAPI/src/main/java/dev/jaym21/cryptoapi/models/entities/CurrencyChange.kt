package dev.jaym21.cryptoapi.models.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrencyChange(
    @Json(name = "market_cap_change")
    val marketCapChange: String?,
    @Json(name = "market_cap_change_pct")
    val marketCapChangePct: String?,
    @Json(name = "price_change")
    val priceChange: String?,
    @Json(name = "price_change_pct")
    val priceChangePct: String?,
    @Json(name = "volume")
    val volume: String?,
    @Json(name = "volume_change")
    val volumeChange: String?,
    @Json(name = "volume_change_pct")
    val volumeChangePct: String?
)