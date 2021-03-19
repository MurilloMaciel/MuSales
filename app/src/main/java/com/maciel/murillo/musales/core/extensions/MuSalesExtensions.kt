package com.maciel.murillo.musales.core.extensions

import android.util.Log
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State

fun String.toState(): State {
    for (state in State.values()) {
        if (state.name == this) {
            return state
        }
    }
    return State.ALL
}

fun String.toCategory(): Category {
    for (category in Category.values()) {
        if (category.name == this) {
            return category
        }
    }
    return Category.ALL
}

fun String?.log(tag: String = "Murillo") {
    Log.d(tag, this.safe())
}