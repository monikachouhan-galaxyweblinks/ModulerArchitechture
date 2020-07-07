package com.aucto.core

import androidx.annotation.StringRes

object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int): String =
        CoreApplication.context.getString(messageId)
}
