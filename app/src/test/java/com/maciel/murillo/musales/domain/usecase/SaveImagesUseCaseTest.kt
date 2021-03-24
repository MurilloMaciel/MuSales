package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.domain.repository.Repository
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveImagesUseCaseTest {

    private val repository: Repository = mockk(relaxed = true)

    private lateinit var saveImagesUseCase: SaveImagesUseCase

    @Before
    fun setUp() {
        saveImagesUseCase = SaveImagesUseCase(repository)
    }

    @Test
    fun `should save images different then null from repository`() = runBlocking {
        val image1 = "image1".toByteArray()
        val image2 = "image2".toByteArray()
        val adId = "adId"
        val imagesBytes = mutableListOf<ByteArray?>(image1, null, image2)

        val result = saveImagesUseCase.invoke(adId, imagesBytes)

        coVerify { repository.saveImage(adId, 0, imagesBytes[0].safe()) }
        coVerify { repository.saveImage(adId, 2, imagesBytes[2].safe()) }
        confirmVerified(repository)
        assertEquals(2, result.size)
    }
}