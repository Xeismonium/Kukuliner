package com.bangkit.kukuliner.di

import android.content.Context
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.api.ApiConfig
import com.bangkit.kukuliner.data.local.room.CulinaryRoomDatabase
import com.bangkit.kukuliner.preference.SettingPreferences
import com.bangkit.kukuliner.preference.dataStore

object Injection {
    fun provideRepository(context: Context): CulinaryRepository {
        val appContext = context.applicationContext
        val pref = SettingPreferences.getInstance(context.dataStore)
        val database = CulinaryRoomDatabase.getInstance(context)
        val culinaryDao = database.culinaryDao()
        val apiService = ApiConfig.getApiService()
        return CulinaryRepository.getInstance(pref, culinaryDao, appContext, apiService)
    }
}