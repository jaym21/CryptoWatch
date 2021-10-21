package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.clients.CryptoCompareClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class CryptoCompareAPITest {

    val api = CryptoCompareClient.api

    @Test
    fun `Get historical data for cryptocurrency`() = runBlocking{
        val response = api.getHistoricalData("BTC", "INR", "30")
        assertNotNull(response.body())
    }

    @Test
    fun `Get latest news of crypto market`() = runBlocking{
        val response = api.getLatestNews()
        assertNotNull(response.body())
    }
}