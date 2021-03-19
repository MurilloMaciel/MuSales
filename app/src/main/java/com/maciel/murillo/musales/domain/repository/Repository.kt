package com.maciel.murillo.musales.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun saveUserUid(uid: String)

    suspend fun readUserUid(): Flow<String>

    suspend fun deleteUserUid()

    fun isUserLogged(): Boolean

    suspend fun signup(email: String, password: String): FirebaseUser?

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun logout()

    suspend fun registerAd(ad: Ad): Ad

    suspend fun getAllAds(): List<Ad>

    suspend fun getAdsFiltered(state: State, category: Category): List<Ad>

    suspend fun getMyAds(userUid: String): List<Ad>

    suspend fun getAd(id: String): Ad

    suspend fun deleteAd(ad: Ad)

    suspend fun updateAd(ad: Ad)

    suspend fun saveImage(adId: String, position: Int, imageBytes: ByteArray): String
}