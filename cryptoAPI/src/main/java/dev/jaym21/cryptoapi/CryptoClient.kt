package dev.jaym21.cryptoapi

import dev.jaym21.cryptoapi.services.NomicsAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CryptoClient {
    private const val BASE_URL = "https://api.nomics.com/v1/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val api by lazy {
        retrofit.create(NomicsAPI::class.java)
    }
}