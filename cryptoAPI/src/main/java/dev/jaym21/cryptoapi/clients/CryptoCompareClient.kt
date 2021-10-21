package dev.jaym21.cryptoapi.clients

import dev.jaym21.cryptoapi.services.CryptoCompareAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CryptoCompareClient {
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/v2/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api by lazy {
        retrofit.create(CryptoCompareAPI::class.java)
    }
}