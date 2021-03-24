package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class SignupUseCase(private val repository: Repository) {

    suspend operator fun invoke(email: String, password: String) {
        val userUid = repository.signup(email, password)
        repository.saveUserUid(userUid)
    }
}