package com.maciel.murillo.musales.core.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.musales.core.listeners.BindableAdapter

@BindingAdapter("app:data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).updateData(data)
    }
}