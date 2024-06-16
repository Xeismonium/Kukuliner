package com.bangkit.kukuliner.ui.main

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getThemeSettings() = repository.getThemeSettings()

    fun getAllCulinary() = repository.getAllCulinary()

    fun searchCulinary(query: String) = repository.searchCulinary(query)

    fun getRecommendationsCulinary(lat: Double, lon: Double) = repository.getRecommendationsCulinary(lat, lon)

    fun saveCulinary(culinary: CulinaryResponseItem) {
        viewModelScope.launch {
            repository.setFavoriteCulinary(culinary, true)
            Log.d("MainViewModel", "Culinary saved: $culinary")
        }
    }

    fun deleteCulinary(culinary: CulinaryResponseItem) {
        viewModelScope.launch {
            repository.setFavoriteCulinary(culinary, false)
            Log.d("MainViewModel", "Culinary deleted: $culinary")
        }
    }

    fun getLastKnownLocation(onLocationRetrieved: (Location?) -> Unit) {
        viewModelScope.launch {
            val location = withContext(Dispatchers.IO) {
                repository.getLastKnownLocation()
            }
            onLocationRetrieved(location)
        }
    }
}
