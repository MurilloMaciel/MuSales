package com.maciel.murillo.musales.presentation.register_ad

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import com.maciel.murillo.musales.domain.usecase.ReadUserIdUseCase
import com.maciel.murillo.musales.domain.usecase.RegisterAdUseCase
import com.maciel.murillo.musales.domain.usecase.SaveImageUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val EMPTY_STRING = ""

class RegisterAdViewModel(
    private val getUserIdUseCase: ReadUserIdUseCase,
    private val registerAdUseCase: RegisterAdUseCase,
    private val saveImageUseCase: SaveImageUseCase,
) : ViewModel() {

    // TODO: 17/03/2021 falta salvar a imagem e usar as imagens
    // TODO: 17/03/2021 falta tratar diferentes erros como erro pegando user id, erro de escrita no firebase, erro de salvamennto da imagem...

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("Murillo", "throwable ${throwable}")
        Log.d("Murillo", "cause ${throwable.cause}")
        Log.d("Murillo", "message ${throwable.message}")
        Log.d("Murillo", "localizedMessage ${throwable.localizedMessage}")
        Log.d("Murillo", "stackTrace ${throwable.stackTrace}")
        _registerAdResult.postValue(Event(false))
    }

    private val defaultContext = Dispatchers.Default + exceptionHandler
    private val saveImageContext = Dispatchers.IO + exceptionHandler

    var title = EMPTY_STRING
    var price = EMPTY_STRING
    var phone = EMPTY_STRING
    var description = EMPTY_STRING

    val states = State.values().map { it.name }
    val categories = Category.values().map { it.name }

    val stateSelected = MutableLiveData<String>().apply { value = states[0] }
    val categorySelected = MutableLiveData<String>().apply { value = categories[0] }

    private val _formInvalid = MutableLiveData<Event<Unit>>()
    val formInvalid: LiveData<Event<Unit>> = _formInvalid

    private val _registerAdResult = MutableLiveData<Event<Boolean>>()
    val registerAdResult: LiveData<Event<Boolean>> = _registerAdResult

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

    private suspend fun readUserId(ad: Ad) = withContext(defaultContext) {
        val userUid = getUserIdUseCase().first()
        if (userUid.isNullOrBlank()) {
            _registerAdResult.postValue(Event(false))
        } else {
            saveAd(ad.apply {
                advertiserId = userUid.safe()
            })
        }
    }

    private suspend fun saveAd(ad: Ad) = withContext(defaultContext) {
        registerAdUseCase(ad)
        _registerAdResult.postValue(Event(true))
    }

    private suspend fun saveImage() = withContext(saveImageContext) {
        // TODO: 17/03/2021 falta salvar as imagens
    }

    private fun startRegisterAdProcess(ad: Ad) {
        viewModelScope.launch {
            readUserId(ad)
        }
    }

    fun onClickRegisterAd() {
        if (isFormValid()) {
            startRegisterAdProcess(
                Ad(
                    images = emptyList(),
                    state = stateSelected.value.safe(),
                    category = categorySelected.value.safe(),
                    title = title,
                    description = description,
                    price = price,
                    advertiserId = ""
                )
            )
        }
    }

    fun onStateChange(newState: String) {
        stateSelected.postValue(newState)
    }

    fun onCategoryChange(newCategory: String) {
        categorySelected.postValue(newCategory)
    }

    fun onClickImage1() {

    }

    fun onClickImage2() {

    }

    fun onClickImage3() {

    }
}