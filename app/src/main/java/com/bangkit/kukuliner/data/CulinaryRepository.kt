package com.bangkit.kukuliner.data

import androidx.lifecycle.LiveData
import com.bangkit.kukuliner.preference.SettingPreferences
import androidx.lifecycle.asLiveData

class CulinaryRepository private constructor(
        private val settingPreference: SettingPreferences
    ) {

    fun getThemeSettings(): LiveData<Boolean> {
        return settingPreference.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreference.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: CulinaryRepository? = null
        fun getInstance(
            settingPreference: SettingPreferences
        ): CulinaryRepository =
            instance ?: synchronized(this) {
                instance ?: CulinaryRepository(settingPreference)
            }.also { instance = it }
    }
}