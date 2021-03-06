package com.maciel.murillo.musales.presentation.register_ad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciel.murillo.musales.core.extensions.getCategoryFromPosition
import com.maciel.murillo.musales.core.extensions.getStateFromPosition
import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.usecase.ReadUserIdUseCase
import com.maciel.murillo.musales.domain.usecase.RegisterAdUseCase
import com.maciel.murillo.musales.domain.usecase.SaveImagesUseCase
import com.maciel.murillo.musales.domain.usecase.UpdateAdUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val EMPTY_STRING = ""

class RegisterAdViewModel(
    private val getUserIdUseCase: ReadUserIdUseCase,
    private val registerAdUseCase: RegisterAdUseCase,
    private val saveImagesUseCase: SaveImagesUseCase,
    private val updateAdUseCase: UpdateAdUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        setNotLoading()
        _registerAdResult.postValue(Event(false))
    }

    private val defaultContext = Dispatchers.Default + exceptionHandler
    private val saveImageContext = Dispatchers.IO + exceptionHandler

    private var imagePositionInEdition = 0
    private val imagesBytes = mutableListOf<ByteArray?>(null, null, null)

    var title = EMPTY_STRING
    var price = EMPTY_STRING
    var phone = EMPTY_STRING
    var description = EMPTY_STRING

    var userUid: String? = null

    private val stateSelected = MutableLiveData<String>()
    private val categorySelected = MutableLiveData<String>()

    private val _formInvalid = MutableLiveData<Event<Unit>>()
    val formInvalid: LiveData<Event<Unit>> = _formInvalid

    private val _registerAdResult = MutableLiveData<Event<Boolean>>()
    val registerAdResult: LiveData<Event<Boolean>> = _registerAdResult

    private val _imageSelection = MutableLiveData<Event<Int>>()
    val imageSelection: LiveData<Event<Int>> = _imageSelection

    private val _registerAdProcessLoading = MutableLiveData<Boolean>()
    val registerAdProcessLoading: LiveData<Boolean> = _registerAdProcessLoading

    private fun setLoading() = _registerAdProcessLoading.postValue(true)

    private fun setNotLoading() = _registerAdProcessLoading.postValue(false)

    private fun isFormValid(): Boolean {
        val isFormValid = title.isNotBlank()
            .and(price.isNotBlank())
            .and(phone.isNotBlank())
            .and(description.isNotBlank())

        if (isFormValid.not()) {
            _formInvalid.postValue(Event(Unit))
        }

        return isFormValid
    }

    private suspend fun readUserId() = withContext(defaultContext) {
        userUid = getUserIdUseCase.invoke()
    }

    private suspend fun saveAd(ad: Ad): Ad {
        var adRegistered: Ad? = null
        withContext(defaultContext) {
            adRegistered = registerAdUseCase(ad)
        }
        return adRegistered ?: Ad()
    }

    private suspend fun saveImages(adId: String, imageBytes: MutableList<ByteArray?>) = withContext(saveImageContext) {
        saveImagesUseCase(adId, imageBytes)
    }

    private fun startRegisterAdProcess(imageBytes: MutableList<ByteArray?>, ad: Ad) {
        viewModelScope.launch {
            setLoading()
            readUserId()
            if (userUid.isNullOrBlank()) {
                _registerAdResult.postValue(Event(false))
            } else {
                val adRegistered = saveAd(ad.apply {
                    advertiserId = userUid.safe()
                })
                val imagesPaths = saveImages(adRegistered.id, imageBytes)
                updateAdUseCase(adRegistered.apply {
                    images = imagesPaths
                })
                _registerAdResult.postValue(Event(true))
            }
            setNotLoading()
        }
    }

    private fun selectImage() {
        _imageSelection.postValue(Event(imagePositionInEdition))
    }

    fun onClickRegisterAd() {
        if (isFormValid()) {
            startRegisterAdProcess(
                imagesBytes,
                Ad(
                    images = emptyList(),
                    state = stateSelected.value.safe(),
                    category = categorySelected.value.safe(),
                    title = title,
                    description = description,
                    price = price,
                    phone = phone,
                    advertiserId = ""
                )
            )
        }
    }

    fun onStateChange(newStatePosition: Int) {
        stateSelected.postValue(newStatePosition.getStateFromPosition().name)
    }

    fun onCategoryChange(newCategoryPosition: Int) {
        categorySelected.postValue(newCategoryPosition.getCategoryFromPosition().name)
    }

    fun onClickImage1() {
        imagePositionInEdition = 0
        selectImage()
    }

    fun onClickImage2() {
        imagePositionInEdition = 1
        selectImage()
    }

    fun onClickImage3() {
        imagePositionInEdition = 2
        selectImage()
    }

    fun onPrepareImage(image: ByteArray) {
        imagesBytes[imagePositionInEdition] = image
    }
}