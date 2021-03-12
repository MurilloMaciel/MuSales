package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class SignupUseCase(private val repository: Repository) {

    suspend operator fun invoke(name: String, email: String, password: String) =
        repository.signup(name, email, password)
}