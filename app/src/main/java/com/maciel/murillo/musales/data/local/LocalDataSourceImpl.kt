package com.maciel.murillo.musales.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maciel.murillo.musales.core.extensions.safe
import com.maciel.murillo.musales.data.datasource.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val KEY_USER_ID = "KEY_USER_ID"

class LocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {

    override suspend fun saveUserUid(uid: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(KEY_USER_ID)] = uid
        }
    }

    override suspend fun readUserUid(): String {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[stringPreferencesKey(KEY_USER_ID)].safe()
        }.first()
    }

    override suspend fun deleteUserUid() {
        saveUserUid("")
    }
}