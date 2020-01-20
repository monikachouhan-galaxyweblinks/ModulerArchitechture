package com.networking.util

import com.networking.date.DateConstants
import java.util.*

object DateUtil {
    fun getCurrentDateTimeUSFormat(timeInterval: Int = 0): String {
        val cal = DateConstants.getCalendar()
        val df = DateConstants.getDateTimeFormatUS()
        cal.add(Calendar.HOUR, timeInterval)
        return df.format(cal.time)
    }


}