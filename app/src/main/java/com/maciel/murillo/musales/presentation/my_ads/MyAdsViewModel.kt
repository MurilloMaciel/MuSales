package com.maciel.murillo.musales.presentation.my_ads

import android.util.Log
import androidx.lifecycle.*
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.usecase.GetMyAdsUseCase
import com.maciel.murillo.musales.domain.usecase.ReadUserIdUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyAdsViewModel(
    private val getMyAdsUseCase: GetMyAdsUseCase,
    private val readUserIdUseCase: ReadUserIdUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _getMyAdsError.postValue(Event(Unit))
    }

    val myAds = MutableLiveData<List<Ad>>().apply { value = emptyList() }

    private val _getMyAdsError = MutableLiveData<Event<Unit>>()
    val getMyAdsError: LiveData<Event<Unit>> = _getMyAdsError

    private val _navigateToRegisterAd = MutableLiveData<Event<Unit>>()
    val navigateToRegisterAd: LiveData<Event<Unit>> = _navigateToRegisterAd

    private val _loadingMyAds = MutableLiveData<Boolean>().apply { value = true }
    val loadingMyAds: LiveData<Boolean> = _loadingMyAds

    private val myAdsEmpty: LiveData<Boolean> = Transformations.map(myAds) { myAds ->
        myAds.isNullOrEmpty()
    }

    val myAdsVisibility = MediatorLiveData<Boolean>().apply {
        addSource(myAdsEmpty) {
            value = it.not()
        }
        addSource(_loadingMyAds) {
            value = it.not()
        }
    }

    val myAdsEmptyVisibility = MediatorLiveData<Boolean>().apply {
        addSource(myAdsEmpty) {
            value = it
        }
        addSource(_loadingMyAds) {
            value = it.not()
        }
    }

    private fun getMyAds() {
        viewModelScope.launch(Dispatchers.Default) {
            _loadingMyAds.postValue(true)
            Log.d("Murillo", "user uid -> ${readUserIdUseCase().first()}")
            myAds.postValue(getMyAdsUseCase(readUserIdUseCase().first()))
            _loadingMyAds.postValue(false)
        }
    }

    fun onClickAdd() {
        _navigateToRegisterAd.postValue(Event(Unit))
    }

    fun onResumeScreen() {
//        getMyAds()
    }
}