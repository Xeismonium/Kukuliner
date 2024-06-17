package com.bangkit.kukuliner.ui.resultscan


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import kotlinx.coroutines.launch

class ResultScanViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun searchCulinary(query: String) = repository.searchCulinary(query)

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
}