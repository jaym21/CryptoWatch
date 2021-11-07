package dev.jaym21.cryptowatch.data

import dev.jaym21.cryptoapi.clients.CryptoCompareClient
import dev.jaym21.cryptoapi.models.entities.Data
import dev.jaym21.cryptoapi.models.entities.NewsData

class CryptoCompareRepository {

    val api = CryptoCompareClient.api

    suspend fun getHistoricalDailyData(requiredCurrency: String, convertTo: String, requiredTime: String): Data? {
        val response = api.getHistoricalDailyData(requiredCurrency, convertTo, requiredTime)
        return response.body()?.data
    }

    suspend fun getHistoricalHourlyData(requiredCurrency: String, convertTo: String): Data? {
        val response = api.getHistoricalHourlyData(requiredCurrency, convertTo)
        return response.body()?.data
    }

    suspend fun getLatestNews(): List<NewsData>? {
        val response = api.getLatestNews()
        return response.body()?.data
    }
}