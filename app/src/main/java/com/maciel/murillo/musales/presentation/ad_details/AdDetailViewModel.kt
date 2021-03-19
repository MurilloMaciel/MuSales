package com.maciel.murillo.musales.presentation.ad_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.domain.model.Ad

class AdDetailViewModel : ViewModel() {

    var ad = Ad()
    var toolbarTitle = MutableLiveData<String>()

    private val _showPhone = MutableLiveData<Event<Unit>>()
    val showPhone: LiveData<Event<Unit>> = _showPhone

    fun onCreatedScreen(ad: Ad) {
        this.ad = ad
        toolbarTitle.postValue(ad.title)
    }

    fun onClickShowPhoneNumber() {
        _showPhone.postValue(Event(Unit))
    }
}