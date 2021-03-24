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

class RegisterAdUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var registerAdUseCase: RegisterAdUseCase

    @Before
    fun setUp() {
        registerAdUseCase = RegisterAdUseCase(repository)
    }

    @Test
    fun `should register ad from repository`() = runBlocking {
        val ad = Ad()
        coEvery { repository.registerAd(ad) } returns ad

        val result = registerAdUseCase.invoke(ad)

        coVerify { repository.registerAd(ad) }
        confirmVerified(repository)
        assertEquals(ad, result)
    }
}