package com.maciel.murillo.musales.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _showToolbar = MutableLiveData<Boolean>().apply { value = false }
    val showToolbar: LiveData<Boolean> = _showToolbar

    private val _toolbarTitle = MutableLiveData<String>().apply { value = "" }
    val toolbarTitle: LiveData<String> = _toolbarTitle

    private fun showToolbar(showToolbar: Boolean) {
        _showToolbar.postValue(showToolbar)
    }

    private fun setToolbarTitle(title: String) {
        _toolbarTitle.postValue(title)
    }

    fun onShowSplashScreen() {
        showToolbar(false)
    }

    fun onShowAuthScreen() {
        showToolbar(false)
    }

    fun onShowAdsScreen() {
        showToolbar(true)
    }

    fun onShowAdDetailsScreen() {
        showToolbar(true)
    }

    fun onShowMyAdsScreen() {
        showToolbar(true)
    }

    fun onShowRegisterAdScreen() {
        showToolbar(true)
    }
}