package com.x3vo.backlogtrackr.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.x3vo.backlogtrackr.network.AuthResponse
import com.x3vo.backlogtrackr.network.BackLogAuthAPIService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val dataStore: DataStore<Preferences>,
    private val backLogAuthAPIService: BackLogAuthAPIService
) {
    private val AUTH_TOKEN = stringPreferencesKey("auth_token")

    val authToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN]
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = token
        }
    }

    suspend fun clearAuthToken() {
        dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN)
        }
    }

    suspend fun makeAuthRequest() {
        try {
            val response = backLogAuthAPIService.getAuthData()
            if (response.access_token.isNotEmpty()) {
                saveAuthToken(response.access_token)
            } else {
                clearAuthToken()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResponse("", -1, "")
        }
    }
}
