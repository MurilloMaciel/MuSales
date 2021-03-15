package com.maciel.murillo.musales.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.maciel.murillo.musales.core.extensions.isEmailValid
import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.core.extensions.safeTrue
import com.maciel.murillo.musales.core.helper.Event
import com.maciel.murillo.musales.domain.usecase.LoginUseCase
import com.maciel.murillo.musales.domain.usecase.SignupUseCase
import com.maciel.murillo.musales.presentation.model.AuthError
import kotlinx.coroutines.*

class AuthViewModel(
    private val signupUseCase: SignupUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        coroutineContext.job.cancel()
        when (throwable) {
            is FirebaseAuthWeakPasswordException -> postSignupError(AuthError.STRONGEST_PASSWORD)
            is FirebaseAuthUserCollisionException -> postSignupError(AuthError.ACCOUNT_ALREADY_EXISTS)
            is FirebaseAuthInvalidUserException -> postSignupError(AuthError.USER_NOT_EXISTS)
            is FirebaseAuthInvalidCredentialsException -> postSignupError(
                if (isSignup.value.safe()) {
                    AuthError.VALID_EMAIL
                } else {
                    AuthError.WRONG_USER_INFO
                }
            )
            else -> postSignupError(AuthError.GENERIC)
        }
    }

    private var job: Job? = null

    private val _signupError = MutableLiveData<Event<AuthError>>()
    val signupError: LiveData<Event<AuthError>> = _signupError

    private val _signupSuccessfull = MutableLiveData<Event<Unit>>()
    val signupSuccessfull: LiveData<Event<Unit>> = _signupSuccessfull

    var isSignup = MutableLiveData<Boolean>().apply { value = true }
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private fun postSignupError(authError: AuthError): Boolean {
        _signupError.postValue(Event(authError))
        return false
    }

    private fun isFormValid(): Boolean {
        return when {
            email.value.isNullOrEmpty() -> postSignupError(AuthError.EMPTY_EMAIL)
            password.value.isNullOrEmpty() -> postSignupError(AuthError.EMPTY_PASSWORD)
            email.value.safe().isEmailValid().not() -> postSignupError(AuthError.EMAIL_INVALID)
            else -> true
        }
    }

    private suspend fun signup() {
        signupUseCase(
            email = email.value.safe(),
            password = password.value.safe()
        )
    }

    private suspend fun login() {
        loginUseCase(
            email = email.value.safe(),
            password = password.value.safe()
        )
    }

    fun onClickSignup() {
        if (isFormValid()) {
            job = viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
                if (isSignup.value.safeTrue()) {
                    signup()
                } else {
                    login()
                }
                _signupSuccessfull.postValue(Event(Unit))
            }
        }
    }
}