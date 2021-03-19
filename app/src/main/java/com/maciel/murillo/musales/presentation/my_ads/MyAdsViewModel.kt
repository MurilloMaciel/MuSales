package com.maciel.murillo.musales.presentation.my_ads

import androidx.lifecycle.*
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.data.model.GetAdsStatus
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.usecase.GetMyAdsUseCase
import com.maciel.murillo.musales.domain.usecase.ReadUserIdUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class MyAdsViewModel(
    private val getMyAdsUseCase: GetMyAdsUseCase,
    private val readUserIdUseCase: ReadUserIdUseCase
) : ViewModel() {

    lateinit var userUid: String

    private val exceptionHandlerReadUserUid = CoroutineExceptionHandler { _, _ ->
        _readUserUidError.postValue(Event(Unit))
    }

    private val exceptionHandlerReadMyAds = CoroutineExceptionHandler { _, _ ->
        setConnectionError()
    }

    val myAds = MutableLiveData<List<Ad>>().apply { value = emptyList() }

    private val _readUserUidError = MutableLiveData<Event<Unit>>()
    val readUserUidError: LiveData<Event<Unit>> = _readUserUidError

    private val _navigateToRegisterAd = MutableLiveData<Event<Unit>>()
    val navigateToRegisterAd: LiveData<Event<Unit>> = _navigateToRegisterAd

    private val _navigateToAdDetails = MutableLiveData<Event<Ad>>()
    val navigateToAdDetails: LiveData<Event<Ad>> = _navigateToAdDetails

    private val _getAdsStatus = MutableLiveData<GetAdsStatus>()

    val myAdsVisibility: LiveData<Boolean> = Transformations.map(_getAdsStatus) { status ->
        myAds.value.isNullOrEmpty().not().and(status == GetAdsStatus.SUCCESS)
    }

    val myAdsEmptyVisibility: LiveData<Boolean> = Transformations.map(_getAdsStatus) { status ->
        myAds.value.isNullOrEmpty().and(status == GetAdsStatus.SUCCESS)
    }

    val loadingVisibility: LiveData<Boolean> = Transformations.map(_getAdsStatus) { status ->
        status == GetAdsStatus.LOADING
    }

    val errorVisibility: LiveData<Boolean> = Transformations.map(_getAdsStatus) { status ->
        status == GetAdsStatus.ERROR
    }

    private fun setLoading() = _getAdsStatus.postValue(GetAdsStatus.LOADING)

    private fun setRequestSuccess() = _getAdsStatus.postValue(GetAdsStatus.SUCCESS)

    private fun setConnectionError() = _getAdsStatus.postValue(GetAdsStatus.ERROR)

    private suspend fun readUserUid() {
        withContext(Dispatchers.Default + exceptionHandlerReadUserUid) {
            userUid = readUserIdUseCase().first()
        }
    }

    private fun getMyAds() {
        viewModelScope.launch(Dispatchers.Default + exceptionHandlerReadMyAds) {
            readUserUid()
            setLoading()
            myAds.postValue(getMyAdsUseCase(userUid))
            setRequestSuccess()
        }
    }

    fun onClickToAdd() {
        _navigateToRegisterAd.postValue(Event(Unit))
    }

    fun onResumeScreen() {
        getMyAds()
    }

    fun onClickAd(position: Int) {
        _navigateToAdDetails.postValue(Event(myAds.value?.get(position) ?: Ad()))
    }
}