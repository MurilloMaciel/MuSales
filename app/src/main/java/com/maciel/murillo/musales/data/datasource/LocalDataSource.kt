package com.maciel.murillo.musales.data.datasource

interface LocalDataSource {

    suspend fun saveUserUid(uid: String)

    suspend fun readUserUid(): String

    suspend fun deleteUserUid()
}