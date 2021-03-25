package com.maciel.murillo.musales.core.extensions

import android.util.Log
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import java.lang.Exception

fun String?.log(tag: String = "Murillo") {
    Log.d(tag, this.safe())
}

fun Int.getCategoryFromPosition() = try {
    Category.values()[this]
} catch (e: Exception) {
    Category.ALL
}

fun Int.getStateFromPosition() = try {
    State.values()[this]
} catch (e: Exception) {
    State.ALL
}