package com.bangkit.kukuliner.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.database.CulinaryEntity
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getFavoriteCulinary() = repository.getFavoriteCulinary()

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