package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class IsUserLoggedUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var isUserLoggedUseCase: IsUserLoggedUseCase

    @Before
    fun setUp() {
        isUserLoggedUseCase = IsUserLoggedUseCase(repository)
    }

    @Test
    fun `should check if user is logged from repository`() = runBlocking {
        val isUserLogged = true
        coEvery { repository.isUserLogged() } returns isUserLogged

        val result = isUserLoggedUseCase.invoke()

        coVerify { repository.isUserLogged() }
        confirmVerified(repository)
        assertEquals(isUserLogged, result)
    }
}