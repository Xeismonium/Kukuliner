package com.bangkit.kukuliner.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getThemeSettings() = repository.getThemeSettings()

}
