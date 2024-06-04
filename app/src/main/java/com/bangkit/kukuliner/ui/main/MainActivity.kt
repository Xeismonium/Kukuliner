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
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityMainBinding
import com.bangkit.kukuliner.data.Culinary
import com.bangkit.kukuliner.data.Result
import com.bangkit.kukuliner.data.local.entity.CulinaryEntity
import com.bangkit.kukuliner.data.local.room.CulinaryRoomDatabase
import com.bangkit.kukuliner.ui.ViewModelFactory
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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getThemeSettings()
        initAdapter()
        initSearchBar()

    }

    private fun getThemeSettings() {
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun initAdapter() {
        val culinaryAdapter = MainAdapter { culinary ->
            if (culinary.isFavorite) {
                viewModel.deleteCulinary(culinary)
            } else {
                viewModel.saveCulinary(culinary)
            }
        }

        getAllCulinary().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {

                    }

                    is Result.Success -> {
                        culinaryAdapter.submitList(result.data)
                    }

                    is Result.Error -> {

                    }
                }
            }
        }

        binding.rvFood.apply {
            setHasFixedSize(true)
            this.adapter = culinaryAdapter
        }
    }

    private fun initSearchBar() {
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
                            val favoriteIntent =
                                Intent(this@MainActivity, FavoriteActivity::class.java)
                            startActivity(favoriteIntent)
                            true
                        }

                        R.id.settings -> {
                            val favoriteIntent =
                                Intent(this@MainActivity, SettingActivity::class.java)
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

    private fun getAllCulinary(): LiveData<Result<List<CulinaryEntity>>> = liveData {
        emit(Result.Loading)
        val culinaryDao = CulinaryRoomDatabase.getInstance(this@MainActivity).culinaryDao()
        try {
            val culinary = loadKulinerFromJson()
            val culinaryList = culinary.map {
                val isFavorited = culinaryDao.isFavorite(it.id)
                CulinaryEntity(
                    it.id,
                    it.name,
                    it.description,
                    it.photoUrl,
                    it.estimatePrice,
                    it.lat,
                    it.lon,
                    isFavorited)
            }
            culinaryDao.deleteAll()
            culinaryDao.insert(culinaryList)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<CulinaryEntity>>> =
            culinaryDao.getCulinary().map { Result.Success(it) }
        emitSource(localData)
    }
}