package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.models.responses.HistoricalResponse
import dev.jaym21.cryptoapi.models.responses.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareAPI {

    @GET("histoday")
    suspend fun getHistoricalDailyData(
        @Query("fsym")
        requiredCurrency: String,
        @Query("limit")
        requiredTime: String,
        @Query("tsym")
        convertTo: String = "INR"
    ): Response<HistoricalResponse>

    @GET("histohour")
    suspend fun getHistoricalHourlyData(
        @Query("fsym")
        requiredCurrency: String,
        @Query("limit")
        requiredTime: String = "24",
        @Query("tsym")
        convertTo: String = "INR"
    ): Response<HistoricalResponse>

    @GET("news/")
    suspend fun getLatestNews(): Response<NewsResponse>
}