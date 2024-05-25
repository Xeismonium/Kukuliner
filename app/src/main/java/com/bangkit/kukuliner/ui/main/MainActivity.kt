package com.bangkit.kukuliner.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityMainBinding
import com.bangkit.kukuliner.data.Kuliner
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        }
    }

    private fun loadKulinerFromJson(): List<Kuliner> {
        val inputStream = resources.openRawResource(R.raw.kukuliner)
        val reader = InputStreamReader(inputStream)
        val jsonElement = JsonParser.parseReader(reader)
        val jsonObject = jsonElement.asJsonObject
        val listKulinerJson = jsonObject.getAsJsonArray("listKuliner")
        val type = object : TypeToken<List<Kuliner>>() {}.type
        return Gson().fromJson(listKulinerJson, type)
    }
}