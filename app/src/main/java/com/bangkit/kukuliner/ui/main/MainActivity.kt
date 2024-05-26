package com.bangkit.kukuliner.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityMainBinding
import com.bangkit.kukuliner.data.Culinary
import com.bangkit.kukuliner.factory.ViewModelFactory
import com.bangkit.kukuliner.ui.favorite.FavoriteActivity
import com.bangkit.kukuliner.ui.setting.SettingActivity
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvKuliner = binding.rvFood
        rvKuliner.setHasFixedSize(true)
        rvKuliner.adapter = MainAdapter(loadKulinerFromJson())

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    false
                }

            searchBar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                Toolbar.OnMenuItemClickListener,
                androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    return when (item.itemId) {
                        R.id.favorite -> {
                            val favoriteIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                            startActivity(favoriteIntent)
                            true
                        }
                        R.id.settings -> {
                            val favoriteIntent = Intent(this@MainActivity, SettingActivity::class.java)
                            startActivity(favoriteIntent)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            })
        }

    }

    private fun loadKulinerFromJson(): List<Culinary> {
        val inputStream = resources.openRawResource(R.raw.kukuliner)
        val reader = InputStreamReader(inputStream)
        val jsonElement = JsonParser.parseReader(reader)
        val jsonObject = jsonElement.asJsonObject
        val listKulinerJson = jsonObject.getAsJsonArray("listKuliner")
        val type = object : TypeToken<List<Culinary>>() {}.type
        return Gson().fromJson(listKulinerJson, type)
    }
}