package com.example.stackexchange.commons.extensitons

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.epochToFormatedDate(formatPattern: String): String {
    val date = Date(this * 1000)
    val formatter: DateFormat = SimpleDateFormat(formatPattern)
    return formatter.format(date)
}