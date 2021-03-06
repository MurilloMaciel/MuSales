package com.maciel.murillo.musales.data.repository

import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.data.datasource.AuthDataSource
import com.maciel.murillo.musales.data.datasource.LocalDataSource
import com.maciel.murillo.musales.data.datasource.RemoteDataSource
import com.maciel.murillo.musales.data.model.*
import com.maciel.murillo.musales.domain.model.Ad
import com.maciel.murillo.musales.domain.model.Category
import com.maciel.murillo.musales.domain.model.State
import com.maciel.murillo.musales.domain.repository.Repository

class RepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val authDataSource: AuthDataSource,
    private val remoteDataSource: RemoteDataSource,
) : Repository {

    override suspend fun saveUserUid(uid: String) = localDataSource.saveUserUid(uid)

    override suspend fun readUserUid() = localDataSource.readUserUid()

    override suspend fun deleteUserUid() = localDataSource.deleteUserUid()

    override fun isUserLogged() = authDataSource.isUserLogged()

    override suspend fun signup(email: String, password: String) = authDataSource.signup(email, password)?.uid.safe()

    override suspend fun login(email: String, password: String) = authDataSource.login(email, password)?.uid.safe()

    override suspend fun logout() = authDataSource.logout()

    override suspend fun registerAd(ad: Ad) = remoteDataSource.registerAd(ad.mapToAdData()).mapToAd()

    override suspend fun getAdsFiltered(state: State, category: Category) =
        remoteDataSource.getAdsFiltered(state.mapToStateData(), category.mapToCategoryData()).map { it.mapToAd() }

    override suspend fun getMyAds(userUid: String) = remoteDataSource.getMyAds(userUid).map { it.mapToAd() }

    override suspend fun getAllAds() = remoteDataSource.getAllAds().map { it.mapToAd() }

    override suspend fun getAd(id: String) = remoteDataSource.getAd(id).mapToAd()

    override suspend fun deleteAd(ad: Ad) = remoteDataSource.deleteAd(ad.mapToAdData())

    override suspend fun updateAd(ad: Ad) = remoteDataSource.updateAd(ad.mapToAdData())

    override suspend fun saveImage(adId: String, position: Int, imageBytes: ByteArray) = remoteDataSource.saveImage(adId, position, imageBytes)
}