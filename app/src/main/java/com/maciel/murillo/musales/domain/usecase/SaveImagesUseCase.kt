package com.maciel.murillo.musales.domain.usecase

import com.maciel.murillo.musales.domain.repository.Repository

class SaveImagesUseCase(private val repository: Repository) {

    suspend operator fun invoke(adId: String, imagesBytes: MutableList<ByteArray?>): List<String> {
        val paths = mutableListOf<String>()
        imagesBytes.forEach { image ->
            image?.run {
                paths.add(repository.saveImage(adId, imagesBytes.indexOf(image), this))
            }
        }
        return paths
    }
}