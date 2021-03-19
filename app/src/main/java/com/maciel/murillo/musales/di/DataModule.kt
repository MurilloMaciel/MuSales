package com.maciel.murillo.musales.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.maciel.murillo.musales.core.extensions.dataStore
import com.maciel.murillo.musales.data.datasource.AuthDataSource
import com.maciel.murillo.musales.data.datasource.LocalDataSource
import com.maciel.murillo.musales.data.datasource.RemoteDataSource
import com.maciel.murillo.musales.data.local.LocalDataSourceImpl
import com.maciel.murillo.musales.data.remote.AuthDataSourceImpl
import com.maciel.murillo.musales.data.remote.RemoteDataSourceImpl
import com.maciel.murillo.musales.data.repository.RepositoryImpl
import com.maciel.murillo.musales.domain.repository.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<StorageReference> { FirebaseStorage.getInstance().reference }
    single<FirebaseFirestore> { Firebase.firestore }
    single<FirebaseAuth> { Firebase.auth }
    single<DataStore<Preferences>> { androidContext().dataStore }
    single<LocalDataSource> { LocalDataSourceImpl(dataStore = get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(db = get(), storage = get()) }
    single<AuthDataSource> { AuthDataSourceImpl(auth = get()) }
    single<Repository> { RepositoryImpl(localDataSource = get(), remoteDataSource = get(), authDataSource = get()) }
}