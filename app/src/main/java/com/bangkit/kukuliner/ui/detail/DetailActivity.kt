package com.bangkit.kukuliner.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import com.bangkit.kukuliner.databinding.ActivityDetailBinding
import com.bangkit.kukuliner.ui.ViewModelFactory
import com.bangkit.kukuliner.ui.main.MainActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val culinary: CulinaryResponseItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("Culinary", CulinaryResponseItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("Culinary")
        }

        Glide.with(this)
            .load(culinary?.photoUrl)
            .into(binding.imgPhotoFood)

        binding.txNameFood.text = culinary?.name
        binding.txDescFood.text = culinary?.description
        binding.txEstimatePrice.text = getString(R.string.estimate_price_rp, culinary?.estimatePrice)

        binding.fabBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        binding.favorite.setImageResource(if (culinary?.isFavorite == false) R.drawable.heart else R.drawable.heart_fill)
        binding.favorite.setOnClickListener {
            if (culinary?.isFavorite == true) {
                viewModel.deleteCulinary(culinary)
                binding.favorite.setImageResource(R.drawable.heart)
            } else {
                viewModel.saveCulinary(culinary as CulinaryResponseItem)
                binding.favorite.setImageResource(R.drawable.heart_fill)
            }
        }
    }
}