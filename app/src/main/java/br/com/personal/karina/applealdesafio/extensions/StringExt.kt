package br.com.personal.karina.applealdesafio.extensions

import android.util.Patterns
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.toDate(patterns: String = "dd/MM/yyyy"): Date? {
    return try {
        val formatter = SimpleDateFormat(patterns, Locale.getDefault())
        formatter.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}