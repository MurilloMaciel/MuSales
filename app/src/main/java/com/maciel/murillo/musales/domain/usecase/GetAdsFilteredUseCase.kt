package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import com.maciel.murillo.musales.domain.repository.Repository

class GetAdsFilteredUseCase(private val repository: Repository) {

    suspend operator fun invoke(state: State, category: Category) = repository.getAdsFiltered(state, category)
}