package dev.jaym21.cryptoapi.services

import dev.jaym21.cryptoapi.clients.NomicsClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class NomicsAPITest {

    val api = NomicsClient.api

    @Test
    fun `Get all cryptocurrencies`() = runBlocking {
        val response = api.getCurrencies()
        assertNotNull(response.body())
    }
}