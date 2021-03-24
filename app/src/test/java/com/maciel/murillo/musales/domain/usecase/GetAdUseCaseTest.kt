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

class GetAdUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var getAdUseCase: GetAdUseCase

    @Before
    fun setUp() {
        getAdUseCase = GetAdUseCase(repository)
    }

    @Test
    fun `should get ad from an id from repository`() = runBlocking {
        val id = "id"
        val ad = Ad(id = id)
        coEvery { repository.getAd(id) } returns ad

        val result = getAdUseCase.invoke(id)

        coVerify { repository.getAd(id) }
        confirmVerified(repository)
        assertEquals(ad, result)
    }
}