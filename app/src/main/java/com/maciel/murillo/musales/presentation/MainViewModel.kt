package com.maciel.murillo.musales.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _showToolbar = MutableLiveData<Boolean>().apply { value = false }
    val showToolbar: LiveData<Boolean> = _showToolbar

}