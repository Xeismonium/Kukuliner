package com.bangkit.kukuliner.ui.detail

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.local.entity.CulinaryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getThemeSettings() = repository.getThemeSettings()

    fun saveCulinary(culinary: CulinaryEntity) {
        viewModelScope.launch {
            repository.setFavoriteCulinary(culinary, true)
        }
    }

    fun deleteCulinary(culinary: CulinaryEntity) {
        viewModelScope.launch {
            repository.setFavoriteCulinary(culinary, false)
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
