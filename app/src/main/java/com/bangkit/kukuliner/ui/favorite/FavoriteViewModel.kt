package com.bangkit.kukuliner.ui.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: CulinaryRepository) : ViewModel() {

    init {
        getFavoriteCulinary()
    }

    fun getFavoriteCulinary() = repository.getFavoriteCulinary()

    fun searchFavoriteCulinary(query: String) = repository.searchFavoriteCulinary(query)

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