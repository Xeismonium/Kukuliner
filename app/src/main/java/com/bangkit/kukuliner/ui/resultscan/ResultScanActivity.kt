package com.bangkit.kukuliner.ui.resultscan

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityResultScanBinding
import com.bangkit.kukuliner.ui.foodscan.FoodScanActivity

class ResultScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result_scan)

        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val label = intent.extras?.getString(FoodScanActivity.LABEL).toString()

        binding.imgPhotoFood.setImageURI(intent.extras?.getString(FoodScanActivity.IMAGE_CLASS)!!.toUri())
        binding.txResultName.text = label
        binding.txPredict.text = "Akurasi: ${intent.extras?.getString(FoodScanActivity.SCORE).toString()}"


    }
}