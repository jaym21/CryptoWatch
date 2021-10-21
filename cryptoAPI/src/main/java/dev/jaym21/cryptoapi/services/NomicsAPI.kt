package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.models.responses.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NomicsAPI {

    @GET("currencies/ticker")
    suspend fun getCurrencies(
        @Query("key")
        key: String = "71ddbd0f0c76c5000cc68ce4ac71e07f28115729",
        @Query("convert")
        convert: String =  "USD",
        @Query("status")
        status: String = "active",
        @Query("sort")
        sort: String = "rank"
    ): Response<List<Currency>>
}