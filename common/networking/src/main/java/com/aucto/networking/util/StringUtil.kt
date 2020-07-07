package com.aucto.networking.util

import androidx.annotation.StringRes
import com.aucto.networking.NetworkingApiApplication
import com.aucto.networking.R

object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int, format: String? = null): String =
        if (format == null) NetworkingApiApplication.context.getString(messageId)
        else NetworkingApiApplication.context.getString(messageId, format)
}

fun readFileFromAssets(fileName: String): String {
    val result = NetworkingApiApplication.context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return result
}

fun getStringResourceByName(aString: String): String {
    val packageName = NetworkingApiApplication.context.packageName
    val resId = NetworkingApiApplication.context.resources
        .getIdentifier(aString, "string", packageName)
    return if (resId == 0) StringUtil.getString(R.string.key_not_found, aString)
    else StringUtil.getString(resId)
}