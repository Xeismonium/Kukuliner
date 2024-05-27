package com.bangkit.kukuliner.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityDetailBinding
import com.bangkit.kukuliner.databinding.ActivityMainBinding
import com.bangkit.kukuliner.factory.ViewModelFactory
import com.bangkit.kukuliner.ui.main.MainActivity
import com.bangkit.kukuliner.ui.main.MainAdapter
import com.bangkit.kukuliner.ui.main.MainViewModel
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<MainViewModel> {
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

        val name = intent.extras?.getString(MainAdapter.NAME_FOOD).toString()
        val desc = intent.extras?.getString(MainAdapter.DESC_FOOD).toString()
        val photo = intent.extras?.getString(MainAdapter.PHOTO_FOOD).toString()
        val price = intent.extras?.getString(MainAdapter.PRICE_FOOD).toString()
        val lat = intent.extras?.getString(MainAdapter.LAT_FOOD).toString()
        val lon = intent.extras?.getString(MainAdapter.LON_FOOD).toString()

        Glide.with(this)
            .load(photo)
            .into(binding.imgPhotoFood)

        binding.txNameFood.text = name
        binding.txDescFood.text = desc
        binding.txEstimatePrice.text = getString(R.string.estimate_price_rp, price)

        binding.fabBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}