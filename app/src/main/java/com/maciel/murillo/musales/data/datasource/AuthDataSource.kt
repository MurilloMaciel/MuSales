package com.maciel.murillo.musales.data.datasource

import com.google.firebase.auth.FirebaseUser

interface AuthDataSource {

    suspend fun isUserLogged(): Boolean

    suspend fun signup(name: String, email: String, password: String): FirebaseUser?

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun logout()
}