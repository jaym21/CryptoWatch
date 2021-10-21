package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareAPI {

    @GET("histoday")
    suspend fun getHistoricalData(
        @Query("fsym")
        requiredCurrency: String,
        @Query("tsym")
        convertTo: String,
        @Query("limit")
        requiredTime: String
    ):Response<CurrencyResponse>
}