package com.maciel.murillo.musales.data.datasource

import com.maciel.murillo.musales.data.model.AdData

interface RemoteDataSource {

    suspend fun registerAd(ad: AdData)

    suspend fun getAllAds(): List<AdData>

    suspend fun getAd(id: String): AdData

    suspend fun deleteAd(ad: AdData)

    suspend fun updateAd(ad: AdData)
}