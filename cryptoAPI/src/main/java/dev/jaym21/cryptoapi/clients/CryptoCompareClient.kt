package dev.jaym21.cryptoapi.clients

import dev.jaym21.cryptoapi.services.CryptoCompareAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object CryptoCompareClient {
    private const val BASE_URL = "https://min-api.cryptocompare.com/data/v2/"

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
        retrofit.create(CryptoCompareAPI::class.java)
    }
}