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

class ReadUserIdUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var readUserIdUseCase: ReadUserIdUseCase

    @Before
    fun setUp() {
        readUserIdUseCase = ReadUserIdUseCase(repository)
    }

    @Test
    fun `should read user uid from repository`() = runBlocking {
        val userUid = "userUid"
        coEvery { repository.readUserUid() } returns userUid

        val result = readUserIdUseCase.invoke()

        coVerify { repository.readUserUid() }
        confirmVerified(repository)
        assertEquals(userUid, result)
    }
}