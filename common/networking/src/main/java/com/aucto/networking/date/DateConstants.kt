package com.aucto.networking.date

import java.text.SimpleDateFormat
import java.util.*

interface DateConstants {
    companion object {

        fun getDayTime12Hour() =
            SimpleDateFormat("EEEE, hh:mm a", getLocale())

        fun getDayTime24Hour() =
            SimpleDateFormat("EEEE, HH:mm", getLocale())

        fun getTimeFormat() =
            SimpleDateFormat("hh:mm a", Locale.getDefault())

        fun getDateTime12Format() =
            SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())


        fun getDateFormat() =
            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        fun getDateTimeFormat() =
            SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault()) // 08/18/2019 11:23:34

        fun getDayTimeFormat() =
            SimpleDateFormat("EEE hh:mm a", getLocale())


        fun getCalendar(): Calendar {
            return Calendar.getInstance().apply { firstDayOfWeek = Calendar.SUNDAY }
        }

        fun getLocale(): Locale {
            return if (Locale.getDefault().toString().contains("zh"))
                Locale.getDefault()
            else Locale.ENGLISH
        }

        fun getDateTimeFormatUS() =
            SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US) // 08/18/2019 11:23:34
    }
}