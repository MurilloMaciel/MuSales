package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetAdsFilteredUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var getAdsFilteredUseCase: GetAdsFilteredUseCase

    @Before
    fun setUp() {
        getAdsFilteredUseCase = GetAdsFilteredUseCase(repository)
    }

    @Test
    fun `should get ads with filter from repository`() = runBlocking {
        val stateFilter = State.RS
        val categoryFilter = Category.CARS
        val ads = emptyList<Ad>()
        coEvery { repository.getAdsFiltered(stateFilter, categoryFilter) } returns ads

        val result = getAdsFilteredUseCase.invoke(stateFilter, categoryFilter)

        coVerify { repository.getAdsFiltered(stateFilter, categoryFilter) }
        confirmVerified(repository)
        assertEquals(ads, result)
    }
}