package dev.jaym21.cryptoapi.clients

import dev.jaym21.cryptoapi.services.NomicsAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val KEY = "71ddbd0f0c76c5000cc68ce4ac71e07f28115729"

object NomicsClient {
    private const val BASE_URL = "https://api.nomics.com/v1/"

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api by lazy {
        retrofit.create(NomicsAPI::class.java)
    }
}