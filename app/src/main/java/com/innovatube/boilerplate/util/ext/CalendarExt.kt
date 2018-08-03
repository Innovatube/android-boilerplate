package com.innovatube.boilerplate.util.ext

import java.text.SimpleDateFormat
import java.util.*

fun Calendar.format(format: String): String {
    val simpleDateFormat = SimpleDateFormat(format, Locale.JAPAN)
    return simpleDateFormat.format(this.time)
}