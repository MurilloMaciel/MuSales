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

class SignupUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var signupUseCase: SignupUseCase

    @Before
    fun setUp() {
        signupUseCase = SignupUseCase(repository)
    }

    @Test
    fun `should register user from repository`() = runBlocking {
        val email = "email"
        val password = "password"
        val userUid = "userUid"
        coEvery { repository.signup(email, password) } returns userUid

        val result = signupUseCase.invoke(email, password)

        coVerify { repository.signup(email, password) }
        coVerify { repository.saveUserUid(userUid) }
        confirmVerified(repository)
        assertEquals(Unit, result)
    }
}