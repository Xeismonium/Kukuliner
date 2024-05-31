package com.bangkit.kukuliner.data

import android.content.Context
import android.content.res.Resources
import androidx.lifecycle.LiveData
import com.bangkit.kukuliner.preference.SettingPreferences
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.database.CulinaryDao
import com.bangkit.kukuliner.database.CulinaryEntity
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

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