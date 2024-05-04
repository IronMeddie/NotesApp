package com.ironmeddie.notes.feature_note.domain.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateTime(onError: String): String {
    return try {
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US)
        val date = Date(this)
        sdf.format(date)
    } catch (e: Exception) {
        onError
    }
}
