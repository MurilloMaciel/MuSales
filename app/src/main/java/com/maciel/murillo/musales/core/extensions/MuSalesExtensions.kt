package com.maciel.murillo.musales.core.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import com.maciel.murillo.musales.R
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

fun Fragment.getStateName(state: State): String = if (state == State.ALL) {
    getString(R.string.all_states)
} else {
    state.name
}

fun Fragment.getCategoryName(category: Category): String = getString(
    when (category) {
        Category.ALL -> R.string.all_categories
        Category.CARS -> R.string.category_cars
        Category.PROPERTIES -> R.string.category_properties
        Category.ELECTRONICS -> R.string.category_electronics
        Category.FASHION -> R.string.category_fasion
        Category.SPORTS -> R.string.category_sports
        Category.MUSIC -> R.string.category_music
        Category.CHILD -> R.string.category_child
        Category.JOBS -> R.string.category_jobs
    }
)