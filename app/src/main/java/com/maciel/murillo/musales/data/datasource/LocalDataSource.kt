package com.maciel.murillo.musales.data.datasource

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveUserUid(uid: String)

    suspend fun readUserUid(): Flow<String>

    suspend fun deleteUserUid()
}