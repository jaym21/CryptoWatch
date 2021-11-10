package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.clients.CryptoCompareClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class CryptoCompareAPITest {

    val api = CryptoCompareClient.api

    @Test
    fun `Get historical daily data for cryptocurrency`() = runBlocking{
        val response = api.getHistoricalDailyData("BTC", "30")
        assertNotNull(response.body())
    }

    @Test
    fun `Get historical hourly data for cryptocurrency`() = runBlocking {
        val response = api.getHistoricalHourlyData("BTC", "24")
        assertNotNull(response.body())
    }

    @Test
    fun `Get latest news of crypto market`() = runBlocking{
        val response = api.getLatestNews()
        assertNotNull(response.body())
    }
}