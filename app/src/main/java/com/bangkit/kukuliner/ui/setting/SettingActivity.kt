package com.bangkit.kukuliner.ui.setting

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivitySettingBinding
import com.bangkit.kukuliner.factory.ViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private val viewModel by viewModels<SettingViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*
         * Switch Night Mode
         */
        val switchTheme = binding.nightmode

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }

        /*
         * Switch Skip Welcome
         */
        val switchSkipWelcome = binding.skipwelcome

        viewModel.getSkipWelcome().observe(this) { isSkipWelcome: Boolean ->
            if (isSkipWelcome) {
                switchSkipWelcome.isChecked = true
            } else {
                switchSkipWelcome.isChecked = false
            }
        }

        switchSkipWelcome.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveSkipWelcome(isChecked)
        }
    }
}