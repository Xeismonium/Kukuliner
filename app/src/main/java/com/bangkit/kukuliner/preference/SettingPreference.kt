package com.bangkit.kukuliner.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val SETTINGS_KEY = booleanPreferencesKey("settings")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[SETTINGS_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_KEY] = isDarkModeActive
        }
    }

    fun getSkipWelcome(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[SETTINGS_KEY] ?: false
        }
    }

    suspend fun saveSkipWelcome(isSkipWelcome: Boolean) {
        dataStore.edit { preferences ->
            preferences[SETTINGS_KEY] = isSkipWelcome
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}