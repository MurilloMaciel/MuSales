package com.maciel.murillo.musales.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.maciel.murillo.musales.data.datasource.AuthDataSource
import com.maciel.murillo.musales.data.datasource.RemoteDataSource
import com.maciel.murillo.musales.data.remote.AuthDataSourceImpl
import com.maciel.murillo.musales.data.remote.RemoteDataSourceImpl
import com.maciel.murillo.musales.data.repository.RepositoryImpl
import com.maciel.murillo.musales.domain.repository.Repository
import org.koin.dsl.module

val dataModule = module {

    single<FirebaseFirestore> { Firebase.firestore }
    single<FirebaseAuth> { Firebase.auth }
    single<RemoteDataSource> { RemoteDataSourceImpl(db = get()) }
    single<AuthDataSource> { AuthDataSourceImpl(auth = get()) }
    single<Repository> { RepositoryImpl(remoteDataSource = get(), authDataSource = get()) }
}