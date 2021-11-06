package dev.jaym21.cryptowatch.data

import dev.jaym21.cryptoapi.clients.KEY
import dev.jaym21.cryptoapi.clients.NomicsClient
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse

class NomicsRepository {

    val api = NomicsClient.api

    suspend fun getCurrencies(): List<CurrencyResponse>? {
        val response = api.getCurrencies()
        return response.body()
    }

    suspend fun getCurrencyDetails(currencyId: String, convertTo: String): List<CurrencyResponse>? {
        val response = api.getCurrencyDetails(KEY, currencyId, convertTo)
        return response.body()
    }
}