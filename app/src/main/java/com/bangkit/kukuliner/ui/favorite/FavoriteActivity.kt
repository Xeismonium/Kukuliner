package com.bangkit.kukuliner.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import com.bangkit.kukuliner.databinding.ActivityFavoriteBinding
import com.bangkit.kukuliner.ui.ViewModelFactory
import com.bangkit.kukuliner.ui.main.MainAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var culinaryAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        culinaryAdapter = MainAdapter { culinary ->
            if (culinary.isFavorite) {
                viewModel.deleteCulinary(culinary)
            } else {
                viewModel.saveCulinary(culinary)
            }
            Log.d("FavoriteActivity", "MainAdapter defined")
        }

        performSearchOrGetFavorites()

        binding.rvFood.apply {
            setHasFixedSize(true)
            adapter = culinaryAdapter
            Log.d("FavoriteActivity", "rvFood defined")
        }

        initSearch()
    }

    private fun performSearchOrGetFavorites() {
        if (binding.searchView.text.isNotEmpty()) {
            searchFavoriteCulinary()
        } else {
            getFavoriteCulinary()
        }
        Log.d("FavoriteActivity", "performSearchOrGetFavorites called")
    }

    private fun getFavoriteCulinary() {
        viewModel.getFavoriteCulinary().observe(this) { favoriteCulinary ->
            culinaryAdapter.submitList(favoriteCulinary)
            updateEmptyView(favoriteCulinary)
        }
        Log.d("FavoriteActivity", "getFavoriteCulinary called")
    }

    private fun searchFavoriteCulinary() {
        viewModel.searchFavoriteCulinary(binding.searchView.text.toString())
            .observe(this) { favoriteCulinary ->
                culinaryAdapter.submitList(favoriteCulinary)
                updateEmptyView(favoriteCulinary)
            }
        Log.d("FavoriteActivity", "searchFavoriteCulinary called")
    }

    private fun initSearch() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                performSearchOrGetFavorites()
                Log.d("FavoriteActivity", "initSearch called")
                false
            }
        }
    }

    private fun updateEmptyView(data: List<CulinaryResponseItem>) {
        if (data.isEmpty()) {
            binding.noData.visibility = View.VISIBLE
        } else {
            binding.noData.visibility = View.GONE
        }
    }
}