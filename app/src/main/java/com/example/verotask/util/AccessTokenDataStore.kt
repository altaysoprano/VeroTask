package com.example.verotask.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AccessTokenDataStore(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "access_token_pref")

    companion object {
        private val AUTH_ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    }

    suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_ACCESS_TOKEN_KEY] = accessToken
        }
    }

    val accessTokenFlow = context.dataStore.data.map { preferences ->
        preferences[AUTH_ACCESS_TOKEN_KEY] ?: ""
    }

    suspend fun getAccessToken(): String {
        return accessTokenFlow.first()
    }
}
