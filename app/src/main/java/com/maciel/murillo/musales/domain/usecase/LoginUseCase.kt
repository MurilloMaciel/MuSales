package com.maciel.murillo.musales.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.domain.repository.Repository

class LoginUseCase(private val repository: Repository) {

    suspend operator fun invoke(email: String, password: String): FirebaseUser? {
        val user = repository.login(email, password)
        repository.saveUserUid(user?.uid.safe())
        return user
    }
}