package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun setUp() {
        logoutUseCase = LogoutUseCase(repository)
    }

    @Test
    fun `should logout user from repository`() = runBlocking {
        val result = logoutUseCase.invoke()

        coVerify { repository.logout() }
        coVerify { repository.deleteUserUid() }
        confirmVerified(repository)
        assertEquals(Unit, result)
    }
}