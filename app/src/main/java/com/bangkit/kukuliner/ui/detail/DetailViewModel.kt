package com.bangkit.kukuliner.ui.detail

import androidx.lifecycle.ViewModel
import com.bangkit.kukuliner.data.CulinaryRepository

class DetailViewModel(private val repository: CulinaryRepository) : ViewModel() {

    fun getThemeSettings() = repository.getThemeSettings()

}
