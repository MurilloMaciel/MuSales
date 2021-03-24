package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UpdateAdUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var updateAdUseCase: UpdateAdUseCase

    @Before
    fun setUp() {
        updateAdUseCase = UpdateAdUseCase(repository)
    }

    @Test
    fun `should update ad from repository`() = runBlocking {
        val ad = Ad(id = "id")

        val result = updateAdUseCase.invoke(ad)

        coVerify { repository.updateAd(ad) }
        confirmVerified(repository)
        assertEquals(Unit, result)
    }
}