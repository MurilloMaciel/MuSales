package com.maciel.murillo.musales.core.extensions

import android.util.Base64

fun String.toBase64() = Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)

fun String.fromBase64() = String(Base64.decode(this, Base64.NO_WRAP))

fun String.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.toDoubleSafe() = if (this.isNullOrEmpty()) 0.0 else this.toDouble()

fun String.fillWithDefaultText(digitsToFill: Int, fillChar: String = "X", rightToLeft: Boolean = true): String {
    var aux = digitsToFill
    var filledString = ""
    while ((aux - this.length) > 0) {
        filledString += fillChar
        aux--
    }
    return if (rightToLeft) (filledString + this) else (this + filledString)
}