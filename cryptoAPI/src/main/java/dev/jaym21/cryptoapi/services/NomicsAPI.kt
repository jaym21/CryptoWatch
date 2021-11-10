package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.clients.KEY
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NomicsAPI {

    @GET("currencies/ticker")
    suspend fun getCurrencies(
        @Query("key")
        key: String = KEY,
        @Query("convert")
        convert: String = "INR",
        @Query("page")
        page: String = "1",
        @Query("status")
        status: String = "active",
        @Query("sort")
        sort: String = "rank",
        @Query("per-page")
        per_page: String = "20"
    ): Response<List<CurrencyResponse>>

    @GET("currencies/ticker")
    suspend fun getCurrencyDetails(
        @Query("key")
        key: String = KEY,
        @Query("ids")
        currencyId: String,
        @Query("convert")
        convert: String = "INR",
    ): Response<List<CurrencyResponse>>
}