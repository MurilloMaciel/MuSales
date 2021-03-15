package com.maciel.murillo.musales.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.maciel.murillo.musales.data.datasource.AuthDataSource
import kotlinx.coroutines.tasks.await

class AuthDataSourceImpl(
    private val auth: FirebaseAuth
) : AuthDataSource {

    override suspend fun isUserLogged() = auth.currentUser != null

    override suspend fun logout() = auth.signOut()

    override suspend fun signup(email: String, password: String): FirebaseUser? {
        try {
            val result: AuthResult = auth.createUserWithEmailAndPassword(email, password).await()
            return result.user
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(email: String, password: String): FirebaseUser? {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            return result.user
        } catch (e: Exception) {
            throw e
        }
    }
}