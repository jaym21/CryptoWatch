package dev.jaym21.cryptowatch.utils

import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

class DateConverterTest {

    @Test
    fun `Get the complete date from unix timestamp`() {
        val date = DateConverter.convertUnixToDate(1635342482)
        assertNotNull(date)
    }

    @Test
    fun `Get the date and month from unix timestamp`() {
        val dateMonth = DateConverter.getDateAndMonth(1635342482)
        assertNotNull(dateMonth)
    }

    @Test
    fun `Get the date with month and year from unix timestamp`() {
        val dateMonthYear = DateConverter.getDateWithMonthAndYear(1635342482)
        assertNotNull(dateMonthYear)
    }

    @Test
    fun `Get the time from unix timestamp`() {
        val time = DateConverter.getTime(1635342482)
        assertNotNull(time)
    }

    @Test
    fun `Get day of the week from unix timestamp`() {
        val dayOfTheWeek = DateConverter.getDayOfWeek(1635342482)
        assertNotNull(dayOfTheWeek)
    }

    @Test
    fun `Get current date`() {
        val currentDate = DateConverter.getCurrentDate()
        assertNotNull(currentDate)
    }

    @Test
    fun `Get time ago for Date`() {
        val timeAgo = DateConverter.getTimeAgo(1635342482)
        assertNotNull(timeAgo)
    }
}