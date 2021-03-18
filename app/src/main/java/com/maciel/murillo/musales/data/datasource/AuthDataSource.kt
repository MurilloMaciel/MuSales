package com.maciel.murillo.musales.data.datasource

import com.google.firebase.auth.FirebaseUser

interface AuthDataSource {

    fun isUserLogged(): Boolean

    suspend fun signup(email: String, password: String): FirebaseUser?

    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun logout()
}