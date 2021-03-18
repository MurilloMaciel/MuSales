package com.maciel.murillo.musales.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.domain.usecase.IsUserLoggedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val SPLASH_TIME = 2500L

class SplashViewModel : ViewModel() {

    private val _navigateToAds = MutableLiveData<Event<Unit>>()
    val navigateToAds: LiveData<Event<Unit>> = _navigateToAds

    fun waitSplashAndRedirect() {
        viewModelScope.launch(Dispatchers.Default) {
            delay(SPLASH_TIME)
            _navigateToAds.postValue(Event(Unit))
        }
    }
}