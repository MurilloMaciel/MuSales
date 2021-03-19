package com.maciel.murillo.musales.presentation.ads

import androidx.lifecycle.*
import com.maciel.murillo.musales.core.extensions.toCategory
import com.maciel.murillo.musales.core.extensions.toState
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.data.model.GetAdsStatus
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import com.maciel.murillo.musales.domain.usecase.GetAdsFilteredUseCase
import com.maciel.murillo.musales.domain.usecase.GetAllAdsUseCase
import com.maciel.murillo.musales.domain.usecase.IsUserLoggedUseCase
import com.maciel.murillo.musales.domain.usecase.LogoutUseCase
import com.maciel.murillo.musales.presentation.model.FilterTypes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdsViewModel(
    private val isUserLoggedUseCase: IsUserLoggedUseCase,
    private val getAllAdsUseCase: GetAllAdsUseCase,
    private val getAdsFilteredUseCase: GetAdsFilteredUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        setConnectionError()
    }

    val ads = MutableLiveData<List<Ad>>().apply { value = emptyList() }
    var filterState: State = State.ALL
    var filterCategory: Category = Category.ALL
    val states = State.values().map { it.name }

    private val _userLogged = MutableLiveData<Event<Boolean>>()
    val userLogged: LiveData<Event<Boolean>> = _userLogged

    private val _filterAds = MutableLiveData<Event<FilterTypes>>()
    val filterAds: LiveData<Event<FilterTypes>> = _filterAds

    private val _logout = MutableLiveData<Event<Unit>>()
    val logout: LiveData<Event<Unit>> = _logout

    private val _navigateToAdDetails = MutableLiveData<Event<Ad>>()
    val navigateToAdDetails: LiveData<Event<Ad>> = _navigateToAdDetails

    private val _navigateToMyAds = MutableLiveData<Event<Unit>>()
    val navigateToMyAds: LiveData<Event<Unit>> = _navigateToMyAds

    private val _navigateToAuth = MutableLiveData<Event<Unit>>()
    val navigateToAuth: LiveData<Event<Unit>> = _navigateToAuth

    private val _getAdsStatus = MutableLiveData<GetAdsStatus>()
    val getAdsStatus: LiveData<GetAdsStatus> = _getAdsStatus

    val adsVisibility: LiveData<Boolean> = Transformations.map(_getAdsStatus) { status ->
        ads.value.isNullOrEmpty().not().and(status == GetAdsStatus.SUCCESS)
    }

    val adsEmptyVisibility: LiveData<Boolean> = Transformations.map(_getAdsStatus) { status ->
        ads.value.isNullOrEmpty().and(status == GetAdsStatus.SUCCESS)
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

    private fun isUserLogged() = isUserLoggedUseCase()

    private suspend fun getAllPublicAds(): List<Ad> = getAllAdsUseCase()

    private suspend fun getAdsFiltered(): List<Ad> = getAdsFilteredUseCase(filterState, filterCategory)

    private suspend fun getAdsHandlingFilter(withFilter: Boolean): List<Ad> {
        return if (withFilter.and(filterState != State.ALL || filterCategory != Category.ALL)) {
            getAdsFiltered()
        } else {
            getAllPublicAds()
        }
    }

    private fun getAds(withFilter: Boolean) {
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
            setLoading()
            ads.postValue(getAdsHandlingFilter(withFilter))
            setRequestSuccess()
        }
    }

    fun onResumeScreen() {
        val isUserLogged = isUserLogged()
        _userLogged.postValue(Event(isUserLogged))
        getAds(withFilter = false)
    }

    fun onClickLocaleFilter() {
        _filterAds.postValue(Event(FilterTypes.STATE))
    }

    fun onClickCategoryFilter() {
        _filterAds.postValue(Event(FilterTypes.CATEGORY))
    }

    fun onRefresh() {
        if (filterCategory == Category.ALL || filterState == State.ALL) {
            getAds(withFilter = false)
        } else {
            getAds(withFilter = true)
        }
    }

    fun onFilterState(stateToFilter: String) {
        filterState = stateToFilter.toState()
        getAds(withFilter = true)
    }

    fun onFilterCategory(categoryToFilter: String) {
        filterCategory = categoryToFilter.toCategory()
        getAds(withFilter = true)
    }

    fun onClickMenuLogout(): Boolean {
        viewModelScope.launch(Dispatchers.Default) {
            logoutUseCase()
            _logout.postValue(Event(Unit))
        }
        return true
    }

    fun onClickMenuMyAds(): Boolean {
        _navigateToMyAds.postValue(Event(Unit))
        return true
    }

    fun onClickMenuLogin(): Boolean {
        _navigateToAuth.postValue(Event(Unit))
        return true
    }

    fun onClickAd(position: Int) {
        _navigateToAdDetails.postValue(Event(ads.value?.get(position) ?: Ad()))
    }
}