package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.CryptoClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class NomicsAPITest {

    val api = CryptoClient.api

    @Test
    fun `Get all cryptocurrencies`() = runBlocking {
        val response = api.getCurrencies()
        assertNotNull(response.body())
    }
}