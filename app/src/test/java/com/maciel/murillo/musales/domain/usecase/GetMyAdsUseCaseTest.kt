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

class GetMyAdsUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var getMyAdsUseCase: GetMyAdsUseCase

    @Before
    fun setUp() {
        getMyAdsUseCase = GetMyAdsUseCase(repository)
    }

    @Test
    fun `should get my ads from repository`() = runBlocking {
        val userId = "userId"
        val ads = emptyList<Ad>()
        coEvery { repository.getMyAds(userId) } returns ads

        val result = getMyAdsUseCase.invoke(userId)

        coVerify { repository.getMyAds(userId) }
        confirmVerified(repository)
        assertEquals(ads, result)
    }
}