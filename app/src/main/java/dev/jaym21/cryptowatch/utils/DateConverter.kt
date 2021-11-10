package dev.jaym21.cryptowatch.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object{
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS

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

        fun getDateWithMonthAndYear(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
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

        fun getCurrentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

        fun getTimeAgo(date: Long): String {
            var newsTime = date

            if (newsTime < 1000000000000L) {
                newsTime *= 1000
            }

            val currentTime = getCurrentDate().time

            val timeDiff = currentTime - newsTime
            return when {
                timeDiff < MINUTE_MILLIS -> "just now"
                timeDiff < 2 * MINUTE_MILLIS -> "a minute ago"
                timeDiff < 60 * MINUTE_MILLIS -> "${timeDiff / MINUTE_MILLIS} minutes ago"
                timeDiff < 2 * HOUR_MILLIS -> "an hour ago"
                timeDiff < 24 * HOUR_MILLIS -> "${timeDiff / HOUR_MILLIS} hours ago"
                timeDiff < 48 * HOUR_MILLIS -> "yesterday"
                else -> "${timeDiff / DAY_MILLIS} days ago"
            }
        }
    }
}