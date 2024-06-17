package com.bangkit.kukuliner.ui.resultscan

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.data.Result
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import com.bangkit.kukuliner.databinding.ActivityResultScanBinding
import com.bangkit.kukuliner.ui.ViewModelFactory
import com.bangkit.kukuliner.ui.foodscan.FoodScanActivity
import com.bangkit.kukuliner.ui.main.MainAdapter
import com.bangkit.kukuliner.ui.main.MainViewModel

class ResultScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultScanBinding
    private val viewModel by viewModels<ResultScanViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var culinaryAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_result_scan)

        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val label = intent.extras?.getString(FoodScanActivity.LABEL).toString()

        binding.imgPhotoFood.setImageURI(intent.extras?.getString(FoodScanActivity.IMAGE_CLASS)!!.toUri())
        binding.txResultName.text = label
        binding.txPredict.text = "Akurasi: ${intent.extras?.getString(FoodScanActivity.SCORE).toString()}"

        initAdapter(label)

        binding.fabBack.setOnClickListener {
            finish()
        }
    }


    private fun initAdapter(label: String) {
        culinaryAdapter = MainAdapter { culinary ->
            if (culinary.isFavorite) {
                viewModel.deleteCulinary(culinary)
            } else {
                viewModel.saveCulinary(culinary)
            }
            Log.d("MainAdapter", "Adapter Defined")
        }

        binding.rvSimiliarFood.apply {
            setHasFixedSize(true)
            this.adapter = culinaryAdapter
            Log.d("MainAdapter", "Applying Adapter")
        }

        searchCulinary(label)
    }


    private fun searchCulinary(query: String) {
        viewModel.searchCulinary(query).observe(this) { result ->
            handleResult(result)
        }
        Log.d("MainAdapter", "Searching Data")
    }

    private fun handleResult(result: Result<List<CulinaryResponseItem>>) {
        when (result) {
            is Result.Loading -> binding.progressBar.visibility = VISIBLE
            is Result.Success -> {
                binding.progressBar.visibility = GONE
                val culinaryData = result.data
                culinaryAdapter.submitList(culinaryData)
                updateEmptyView(culinaryData)
            }
            is Result.Error -> {
                binding.progressBar.visibility = GONE
                Toast.makeText(this, "Gagal ambil data ${result.error} ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateEmptyView(data: List<CulinaryResponseItem>) {
        binding.noData.visibility = if (data.isEmpty()) VISIBLE else GONE
    }
}