package dev.jaym21.cryptowatch.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object{
        fun convertUnixToDate(timestamp: Long): String {
            //format of the date
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)
            //setting time zone to GMT + 5:30(IST)
            sdf.timeZone = TimeZone.getTimeZone("IST")
            return sdf.format(timestamp * 1000)
        }

        fun getDateAndMonth(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd/MM", Locale.ENGLISH)
            return sdf.format(timestamp * 1000)
        }

        fun getTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
            return sdf.format(timestamp * 1000)
        }

        fun getDayOfWeek(timestamp: Long): String {
            val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
            return sdf.format(timestamp * 1000)
        }
    }
}