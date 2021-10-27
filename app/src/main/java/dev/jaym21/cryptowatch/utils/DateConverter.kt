package dev.jaym21.cryptowatch.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object{
        fun convertUnixToDate(unix: String): String {
            val unixSeconds = unix.toLong()
            //convert seconds to milliseconds
            val date = Date(unixSeconds * 1000L)
            //format of the date
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a")
            //setting time zone to GMT + 5:30(IST)
            sdf.timeZone = TimeZone.getTimeZone("IST")
            return sdf.format(date)
        }

        fun getDayOfWeek(timestamp: Long): String {
            val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
            return sdf.format(timestamp * 1000)
        }

    }
}