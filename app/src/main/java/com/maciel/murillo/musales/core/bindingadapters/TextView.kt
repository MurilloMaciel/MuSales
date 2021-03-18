package com.maciel.murillo.musales.core.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:textStringId")
fun textStringId(textView: TextView, stringId: Int?) {
    stringId?.let { id ->
        textView.text = textView.context.getString(id)
    }
}