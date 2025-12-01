package com.example.monpad.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.monpad.network.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class TokenManager(private val context: Context) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")
        private val USER_ROLE = stringPreferencesKey("user_role")
    }

    // Simpan token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Simpan user info berdasarkan role
    suspend fun saveUserInfo(user: User, role: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user.id.toString()
            preferences[USER_NAME] = user.username
            preferences[USER_EMAIL] = user.email
            preferences[USER_ROLE] = role
        }
    }

    // Ambil token (Flow)
    val token: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    // Ambil user name (Flow)
    val userName: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }

    // Ambil user role (Flow)
    val userRole: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_ROLE]
    }

    // ⭐ TAMBAHKAN fungsi suspend untuk mendapatkan role secara langsung
    suspend fun getUserRole(): String? {
        return userRole.firstOrNull()
    }

    // ⭐ TAMBAHKAN fungsi suspend untuk mendapatkan token secara langsung
    suspend fun getToken(): String? {
        return token.firstOrNull()
    }

    // ⭐ TAMBAHKAN fungsi suspend untuk cek login status
    suspend fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    // Hapus semua data (logout)
    suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

private fun DataStore<Preferences>.edit(transform: suspend (MutablePreferences) -> Unit) {}