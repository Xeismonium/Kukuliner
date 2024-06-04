package com.bangkit.kukuliner.data

import androidx.lifecycle.LiveData
import com.bangkit.kukuliner.preference.SettingPreferences
import androidx.lifecycle.asLiveData
import com.bangkit.kukuliner.data.local.room.CulinaryDao
import com.bangkit.kukuliner.data.local.entity.CulinaryEntity

class CulinaryRepository private constructor(
    private val settingPreference: SettingPreferences,
    private val culinaryDao: CulinaryDao,
) {

    fun getThemeSettings(): LiveData<Boolean> {
        return settingPreference.getThemeSetting().asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreference.saveThemeSetting(isDarkModeActive)
    }

    fun getSkipWelcome(): LiveData<Boolean> {
        return settingPreference.getSkipWelcome().asLiveData()
    }

    suspend fun saveSkipWelcome(isSkipWelcome: Boolean) {
        settingPreference.saveSkipWelcome(isSkipWelcome)
    }

    fun getCulinary(): LiveData<List<CulinaryEntity>> {
        return culinaryDao.getCulinary()
    }

    fun getFavoriteCulinary(): LiveData<List<CulinaryEntity>> {
        return culinaryDao.getFavoriteCulinary()
    }

    suspend fun setFavoriteCulinary(culinary: CulinaryEntity, newState: Boolean) {
        culinary.isFavorite = newState
        culinaryDao.update(culinary)
    }

    companion object {
        @Volatile
        private var instance: CulinaryRepository? = null
        fun getInstance(
            settingPreference: SettingPreferences,
            culinaryDao: CulinaryDao
        ): CulinaryRepository =
            instance ?: synchronized(this) {
                instance ?: CulinaryRepository(settingPreference, culinaryDao)
            }.also { instance = it }
    }
}