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

class LoginUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(repository)
    }

    @Test
    fun `should login user from repository`() = runBlocking {
        val email = "email"
        val password = "password"
        val userUid = "userUid"
        coEvery { repository.login(email, password) } returns userUid

        val result = loginUseCase.invoke(email, password)

        coVerify { repository.login(email, password) }
        coVerify { repository.saveUserUid(userUid) }
        confirmVerified(repository)
        assertEquals(Unit, result)
    }
}