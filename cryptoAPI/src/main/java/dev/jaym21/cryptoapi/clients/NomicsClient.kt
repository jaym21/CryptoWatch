package dev.jaym21.cryptoapi.clients

import dev.jaym21.cryptoapi.services.NomicsAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val KEY = "71ddbd0f0c76c5000cc68ce4ac71e07f28115729"

object NomicsClient {
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