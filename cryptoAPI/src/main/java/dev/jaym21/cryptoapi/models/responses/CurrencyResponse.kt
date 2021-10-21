package dev.jaym21.cryptoapi.models.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.jaym21.cryptoapi.models.entities.Currency

@JsonClass(generateAdapter = true)
data class CurrencyResponse(
    val currency: List<Currency>
)