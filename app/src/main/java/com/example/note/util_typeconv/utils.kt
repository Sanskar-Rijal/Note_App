package com.example.note.util_typeconv

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(time:Long):String{
    val date = Date(time)
    val format = SimpleDateFormat("EEE, d MMM hh:mm aaa",
        Locale.getDefault())

    return format.format(date)
}