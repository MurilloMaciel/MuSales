package com.maciel.murillo.musales.data.repository

import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.musales.data.datasource.AuthDataSource
import com.maciel.murillo.musales.data.datasource.RemoteDataSource
import com.maciel.murillo.musales.data.model.AdData
import com.maciel.murillo.musales.data.model.mapToAd
import com.maciel.murillo.musales.data.model.mapToAdData
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.repository.Repository

class RepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val remoteDataSource: RemoteDataSource,
) : Repository {

    override suspend fun isUserLogged() = authDataSource.isUserLogged()

    override suspend fun signup(name: String, email: String, password: String) = authDataSource.signup(name, email, password)

    override suspend fun login(email: String, password: String) = authDataSource.login(email, password)

    override suspend fun logout() = authDataSource.logout()

    override suspend fun registerAd(ad: Ad) = remoteDataSource.registerAd(ad.mapToAdData())

    override suspend fun getAllAds() = remoteDataSource.getAllAds().map { it.mapToAd() }

    override suspend fun getAd(id: String) = remoteDataSource.getAd(id).mapToAd()

    override suspend fun deleteAd(ad: Ad) = remoteDataSource.deleteAd(ad.mapToAdData())

    override suspend fun updateAd(ad: Ad) = remoteDataSource.updateAd(ad.mapToAdData())
}