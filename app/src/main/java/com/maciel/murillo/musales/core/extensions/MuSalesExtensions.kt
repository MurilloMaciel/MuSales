package com.maciel.murillo.musales.core.extensions

import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State

//fun State.toStringArray

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

//fun String.toState() = when (this) {
//    else -> State.ALL
//}

//fun String.toCategory() = when (this) {
//    else -> Category.ALL
//}