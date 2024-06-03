package com.bangkit.kukuliner.ui.favorite

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityFavoriteBinding
import com.bangkit.kukuliner.factory.ViewModelFactory
import com.bangkit.kukuliner.ui.main.MainAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val culinaryAdapter = MainAdapter { culinary ->
            if (culinary.isFavorite) {
                viewModel.deleteCulinary(culinary)
            } else {
                viewModel.saveCulinary(culinary)
            }
        }

        viewModel.getFavoriteCulinary().observe(this) { favoriteCulinary ->
            culinaryAdapter.submitList(favoriteCulinary)
        }

        binding.rvFood.apply {
            setHasFixedSize(true)
            adapter = culinaryAdapter
        }
    }
}