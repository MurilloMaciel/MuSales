package com.maciel.murillo.musales.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.maciel.murillo.musales.data.datasource.RemoteDataSource
import com.maciel.murillo.musales.data.model.AdData
import com.maciel.murillo.musales.data.model.CategoryData
import com.maciel.murillo.musales.data.model.StateData
import kotlinx.coroutines.tasks.await

const val DOCUMENT_ADS = "Ads"
const val DOCUMENT_MY_ADS = "My_Ads"

class RemoteDataSourceImpl(private val db: FirebaseFirestore) : RemoteDataSource {

    override suspend fun registerAd(ad: AdData) {
        db.collection(DOCUMENT_ADS).document().let { document ->
            document.set(ad.apply { id = document.id }).await()
        }
    }

    override suspend fun getAdsFiltered(state: StateData, category: CategoryData): List<AdData> {
        val ads = mutableListOf<AdData>()
        return ads
    }

    override suspend fun getMyAds(): List<AdData> {
        val ads = mutableListOf<AdData>()
        return ads
    }

    override suspend fun getAllAds(): List<AdData> {
        val snapshot = db.collection(DOCUMENT_ADS)
            .get()
            .await()
        val ads = mutableListOf<AdData>()
        snapshot.documents.forEach { document ->
            document.toObject<AdData>()?.run {
                ads.add(this).apply { id = document.id }
            }
        }
        return ads
    }

    override suspend fun deleteAd(ad: AdData) {
        db.collection(DOCUMENT_ADS)
            .document(ad.id)
            .delete()
            .await()
    }

    override suspend fun updateAd(ad: AdData) {
        db.collection(DOCUMENT_ADS)
            .document(ad.id)
            .set(ad)
            .await()
    }

    override suspend fun getAd(id: String): AdData {
        val snapshot = db.collection(DOCUMENT_ADS)
            .document(id)
            .get()
            .await()
        return snapshot.toObject<AdData>() ?: throw Exception()
    }
}