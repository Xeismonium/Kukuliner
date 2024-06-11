package com.bangkit.kukuliner.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getFavoriteCulinary() = repository.getFavoriteCulinary()

    fun saveCulinary(culinary: CulinaryResponseItem) {
        viewModelScope.launch {
            repository.setFavoriteCulinary(culinary, true)
            Log.d("FavoriteViewModel", "Culinary saved: ${culinary.name}")
        }
    }

    fun deleteCulinary(culinary: CulinaryResponseItem) {
        viewModelScope.launch {
            repository.setFavoriteCulinary(culinary, false)
            Log.d("FavoriteViewModel", "Culinary deleted: ${culinary.name}")
        }
    }
}