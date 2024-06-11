package com.bangkit.kukuliner.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.bangkit.kukuliner.preference.SettingPreferences
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bangkit.kukuliner.data.remote.api.ApiService
import com.bangkit.kukuliner.data.local.room.CulinaryDao
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

class CulinaryRepository private constructor(
    private val settingPreference: SettingPreferences,
    private val culinaryDao: CulinaryDao,
    context: Context,
    private val apiService: ApiService,
) {

    private val appContext = context.applicationContext
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context.applicationContext)

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

    fun getCulinary(): LiveData<Result<List<CulinaryResponseItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getCulinary()
            val culinaryList = response.map { culinary ->
                val isFavorite = culinaryDao.isFavorite(culinary.id)
                CulinaryResponseItem(
                    culinary.id,
                    culinary.name,
                    culinary.description,
                    culinary.photoUrl,
                    culinary.estimatePrice,
                    culinary.lat,
                    culinary.lon,
                    isFavorite
                )
            }
            culinaryDao.deleteAll()
            culinaryDao.insert(culinaryList)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("CulinaryRepository", "getCulinary: ${e.message.toString()} ")
            emit(Result.Error("${e.message}"))
        }
        val localData: LiveData<Result<List<CulinaryResponseItem>>> =
            culinaryDao.getCulinary().map { Result.Success(it) }
        emitSource(localData)
    }

    fun searchCulinary(query: String): LiveData<List<CulinaryResponseItem>> {
        return culinaryDao.searchCulinary(query)
    }

    fun getFavoriteCulinary(): LiveData<List<CulinaryResponseItem>> {
        return culinaryDao.getFavoriteCulinary()
    }

    suspend fun setFavoriteCulinary(culinary: CulinaryResponseItem, newState: Boolean) {
        culinary.isFavorite = newState
        culinaryDao.update(culinary)
    }

    suspend fun getLastKnownLocation(): Location? {
        return try {
            if (ContextCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.await()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        @Volatile
        private var instance: CulinaryRepository? = null
        fun getInstance(
            settingPreference: SettingPreferences,
            culinaryDao: CulinaryDao,
            context: Context,
            apiService: ApiService,
        ): CulinaryRepository =
            instance ?: synchronized(this) {
                instance ?: CulinaryRepository(settingPreference, culinaryDao, context, apiService)
            }.also { instance = it }
    }
}