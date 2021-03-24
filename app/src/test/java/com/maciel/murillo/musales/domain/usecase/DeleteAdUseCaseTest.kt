package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteAdUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var deleteAdUseCase: DeleteAdUseCase

    @Before
    fun setUp() {
        deleteAdUseCase = DeleteAdUseCase(repository)
    }

    @Test
    fun `should delete ad from repository`() = runBlocking {
        val ad = Ad(id = "id")

        val result = deleteAdUseCase.invoke(ad)

        coVerify { repository.deleteAd(ad) }
        confirmVerified(repository)
        Assert.assertEquals(Unit, result)
    }
}