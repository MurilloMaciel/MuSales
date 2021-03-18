package com.maciel.murillo.musales.core.bindingadapters

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.maciel.murillo.musales.core.extensions.SpinnerExtensions
import com.maciel.murillo.musales.core.extensions.SpinnerExtensions.setSpinnerEntries
import com.maciel.murillo.musales.core.extensions.SpinnerExtensions.setSpinnerItemSelectedListener
import com.maciel.murillo.musales.core.extensions.SpinnerExtensions.setSpinnerValue

@BindingAdapter("app:entries")
fun Spinner.setEntries(entries: List<Any>?) {
    setSpinnerEntries(entries)
}

@BindingAdapter("app:onItemSelected")
fun Spinner.setItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
    setSpinnerItemSelectedListener(itemSelectedListener)
}

@BindingAdapter("app:newValue")
fun Spinner.setNewValue(newValue: Any?) {
    setSpinnerValue(newValue)
}