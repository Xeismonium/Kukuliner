package com.bangkit.kukuliner.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityDetailBinding
import com.bangkit.kukuliner.databinding.ActivityMainBinding
import com.bangkit.kukuliner.ui.main.MainActivity
import com.bangkit.kukuliner.ui.main.MainAdapter
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.txEstimatePrice.text = "Estimasi Harga: $price"

        binding.fabBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}