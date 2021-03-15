package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class LogoutUseCase(private val repository: Repository) {

    suspend operator fun invoke() {
        repository.logout()
        repository.deleteUserUid()
    }
}