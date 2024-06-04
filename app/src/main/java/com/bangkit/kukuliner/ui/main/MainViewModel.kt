package com.bangkit.kukuliner.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.local.entity.CulinaryEntity
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CulinaryRepository) : ViewModel() {

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
}
