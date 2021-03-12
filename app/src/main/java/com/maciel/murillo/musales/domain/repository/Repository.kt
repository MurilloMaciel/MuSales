package com.maciel.murillo.musales.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.musales.domain.model.Ad

interface Repository {

    suspend fun isUserLogged(): Boolean

    suspend fun signup(name: String, email: String, password: String): FirebaseUser?

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun logout()

    suspend fun registerAd(ad: Ad)

    suspend fun getAllAds(): List<Ad>

    suspend fun getAd(id: String): Ad

    suspend fun deleteAd(ad: Ad)

    suspend fun updateAd(ad: Ad)
}