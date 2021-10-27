package dev.jaym21.cryptowatch.utils

import org.junit.Assert.assertNotNull
import org.junit.Test

class DateConverterTest {

    @Test
    fun `Get the complete date from unix timestamp`() {
        val date = DateConverter.convertUnixToDate(1635342482)
        assertNotNull(date)
    }

    @Test
    fun `Get the date and month from unix timestamp`() {
        val date = DateConverter.getDateAndMonth(1635342482)
        assertNotNull(date)
    }
}