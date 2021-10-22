package dev.jaym21.cryptowatch.data

import dev.jaym21.cryptoapi.clients.NomicsClient
import dev.jaym21.cryptoapi.models.responses.Currency

class NomicsRepository {

    val api = NomicsClient.api

    suspend fun getCurrencies(): List<Currency>? {
        val response = api.getCurrencies()
        return response.body()
    }
}