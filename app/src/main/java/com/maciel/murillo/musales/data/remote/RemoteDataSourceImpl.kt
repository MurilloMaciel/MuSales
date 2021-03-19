package com.maciel.murillo.musales.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.StorageReference
import com.maciel.murillo.musales.data.datasource.RemoteDataSource
import com.maciel.murillo.musales.data.model.AdData
import com.maciel.murillo.musales.data.model.CategoryData
import com.maciel.murillo.musales.data.model.StateData
import kotlinx.coroutines.tasks.await

const val DOCUMENT_ADS = "Ads"
const val PROPERTY_USER_UID = "userUid"
const val PROPERTY_STATE = "state"
const val PROPERTY_CATEGORY = "category"
const val STORAGE_NODE_IMAGES = "images"
const val STORAGE_NODE_ADS = "ads"
const val STORAGE_PREFIX_IMAGE = "image"

class RemoteDataSourceImpl(
    private val storage: StorageReference,
    private val db: FirebaseFirestore
) : RemoteDataSource {

    override suspend fun registerAd(ad: AdData): AdData {
        db.collection(DOCUMENT_ADS).document().let { document ->
            document.set(ad.apply { id = document.id }).await()
            return ad
        }
    }

    override suspend fun getAdsFiltered(state: StateData, category: CategoryData): List<AdData> {
        val ads = mutableListOf<AdData>()
        val snapshot = when {
            state == StateData.ALL -> getAdsFilteredByCategory(category)
            category == CategoryData.ALL -> getAdsFilteredByState(state)
            else -> getAdsFilteredByStateAndCategory(state, category)
        }
        snapshot.documents.forEach { document ->
            document.toObject<AdData>()?.run {
                ads.add(this).apply { id = document.id }
            }
        }
        return ads
    }

    override suspend fun getMyAds(userUid: String): List<AdData> {
        val snapshot = db.collection(DOCUMENT_ADS)
            .whereEqualTo(PROPERTY_USER_UID, userUid)
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

    override suspend fun saveImage(adId: String, position: Int, imageBytes: ByteArray): String {
        val uploadTask = storage.child(STORAGE_NODE_IMAGES)
            .child(STORAGE_NODE_ADS)
            .child(adId)
            .child(STORAGE_PREFIX_IMAGE + position)
            .putBytes(imageBytes)
            .await()
        return uploadTask
            .storage
            .downloadUrl
            .await()
            .toString()
    }

    override suspend fun getAd(id: String): AdData {
        val snapshot = db.collection(DOCUMENT_ADS)
            .document(id)
            .get()
            .await()
        return snapshot.toObject<AdData>() ?: throw Exception()
    }

    private suspend fun getAdsFilteredByStateAndCategory(state: StateData, category: CategoryData) = db.collection(DOCUMENT_ADS)
        .whereEqualTo(PROPERTY_STATE, state.name)
        .whereEqualTo(PROPERTY_CATEGORY, category.name)
        .get()
        .await()

    private suspend fun getAdsFilteredByState(state: StateData) = db.collection(DOCUMENT_ADS)
        .whereEqualTo(PROPERTY_STATE, state.name)
        .get()
        .await()

    private suspend fun getAdsFilteredByCategory(category: CategoryData) = db.collection(DOCUMENT_ADS)
        .whereEqualTo(PROPERTY_CATEGORY, category.name)
        .get()
        .await()
}