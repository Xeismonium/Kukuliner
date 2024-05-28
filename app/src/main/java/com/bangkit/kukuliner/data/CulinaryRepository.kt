package com.bangkit.kukuliner.data

import androidx.lifecycle.LiveData
import com.bangkit.kukuliner.preference.SettingPreferences
import androidx.lifecycle.asLiveData
import com.bangkit.kukuliner.database.Culinary
import com.bangkit.kukuliner.database.CulinaryDao

class CulinaryRepository private constructor(
    private val settingPreference: SettingPreferences,
    private val culinaryDao: CulinaryDao
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

    fun getAllCulinary(): LiveData<List<Culinary>> {
        return culinaryDao.getAllCulinary()
    }

    suspend fun insertCulinary(culinary: Culinary) {
        culinaryDao.insert(culinary)
    }

    suspend fun deleteCulinary(culinary: Culinary) {
        culinaryDao.delete(culinary)
    }

    suspend fun updateCulinary(culinary: Culinary) {
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