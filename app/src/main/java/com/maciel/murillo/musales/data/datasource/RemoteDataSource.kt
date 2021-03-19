package com.maciel.murillo.musales.data.datasource

import com.maciel.murillo.musales.data.model.AdData
import com.maciel.murillo.musales.data.model.CategoryData
import com.maciel.murillo.musales.data.model.StateData

interface RemoteDataSource {

    suspend fun registerAd(ad: AdData): AdData

    suspend fun getAllAds(): List<AdData>

    suspend fun getAdsFiltered(state: StateData, category: CategoryData): List<AdData>

    suspend fun getMyAds(userUid: String): List<AdData>

    suspend fun getAd(id: String): AdData

    suspend fun deleteAd(ad: AdData)

    suspend fun updateAd(ad: AdData)

    suspend fun saveImage(adId: String, position: Int, imageBytes: ByteArray): String

//    suspend fun prepareImage(imagePath: String): ByteArray
}