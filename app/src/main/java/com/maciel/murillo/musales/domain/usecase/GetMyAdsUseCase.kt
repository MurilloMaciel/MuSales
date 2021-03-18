package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class GetMyAdsUseCase(private val repository: Repository) {

    suspend operator fun invoke(userUid: String) = repository.getMyAds(userUid)
}