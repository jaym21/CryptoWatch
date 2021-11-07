package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.clients.KEY
import dev.jaym21.cryptoapi.clients.NomicsClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class NomicsAPITest {

    val api = NomicsClient.api

    @Test
    fun `Get all cryptocurrencies`() = runBlocking {
        val response = api.getCurrencies(KEY, "INR", "1")
        assertNotNull(response.body())
    }

    @Test
    fun `Get total currencies`() = runBlocking {
        val response = api.getTotalCurrencies()
        assertNotNull(response.body())
    }

    @Test
    fun `Get currency details`() = runBlocking {
        val response = api.getCurrencyDetails(KEY, "BTC", "USD")
        assertNotNull(response.body())
    }
}