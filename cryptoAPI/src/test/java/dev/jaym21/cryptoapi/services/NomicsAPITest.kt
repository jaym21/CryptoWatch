package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.clients.CryptoCompareClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class NomicsAPITest {

    val api = CryptoCompareClient.api

    @Test
    fun `Get all cryptocurrencies`() = runBlocking {
        val response = api.getCurrencies()
        assertNotNull(response.body())
    }
}