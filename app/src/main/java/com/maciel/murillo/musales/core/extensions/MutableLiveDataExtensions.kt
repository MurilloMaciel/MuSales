package com.maciel.murillo.musales.core.extensions

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(item)
    this.postValue(value)
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.postValue(this.value)
}