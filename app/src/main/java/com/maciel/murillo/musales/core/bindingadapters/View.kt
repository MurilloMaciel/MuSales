package com.maciel.murillo.musales.core.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:is_visible")
fun isVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}