package com.bangkit.kukuliner.ui.welcome

import androidx.lifecycle.ViewModel
import com.bangkit.kukuliner.data.CulinaryRepository

class SplashScreenViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getSkipWelcome() = repository.getSkipWelcome()
    fun getThemeSettings() = repository.getThemeSettings()

}