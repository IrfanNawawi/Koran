package com.mockdroid.koran.utils

import java.text.ParseException
import java.text.SimpleDateFormat

object DateFormat {
    private fun formatDate(date: String, format: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)

            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun getShortDate(date: String?): String {
        return formatDate(date.toString(), "dd MMM yyyy")
    }
}