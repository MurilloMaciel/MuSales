package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetAllAdsUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var getAllAdsUseCase: GetAllAdsUseCase

    @Before
    fun setUp() {
        getAllAdsUseCase = GetAllAdsUseCase(repository)
    }

    @Test
    fun `should get all ads from repository`() = runBlocking {
        val ads = emptyList<Ad>()
        coEvery { repository.getAllAds() } returns ads

        val result = getAllAdsUseCase.invoke()

        coVerify { repository.getAllAds() }
        confirmVerified(repository)
        assertEquals(ads, result)
    }
}