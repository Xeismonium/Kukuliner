package com.bangkit.kukuliner.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.kukuliner.data.CulinaryRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getThemeSettings() = repository.getThemeSettings()
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repository.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getSkipWelcome() = repository.getSkipWelcome()
    fun saveSkipWelcome(isSkipWelcome: Boolean) {
        viewModelScope.launch {
            repository.saveSkipWelcome(isSkipWelcome)
        }
    }
}
