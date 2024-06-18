package com.bangkit.kukuliner.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.data.Result
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem
import com.bangkit.kukuliner.databinding.ActivityMainBinding
import com.bangkit.kukuliner.ui.ViewModelFactory
import com.bangkit.kukuliner.ui.favorite.FavoriteActivity
import com.bangkit.kukuliner.ui.foodscan.FoodScanActivity
import com.bangkit.kukuliner.ui.setting.SettingActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var culinaryAdapter: MainAdapter
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }

        initAdapter()
        initSearchBar()
        initFoodScan()
        refreshData()
    }

    /*
     * Init Adapter
     */

    private fun initAdapter() {

        culinaryAdapter = MainAdapter { culinary ->
            if (culinary.isFavorite) {
                viewModel.deleteCulinary(culinary)
            } else {
                viewModel.saveCulinary(culinary)
            }
        }

        binding.rvFood.apply {
            setHasFixedSize(true)
            this.adapter = culinaryAdapter
        }

        performFetchBasedOnState()
    }

    private fun performFetchBasedOnState() {
        val query = binding.searchView.text.toString()
        if (query.isEmpty()) {
            viewModel.getLastKnownLocation { location: Location? ->
                if (location == null) {
                    fetchAllCulinary()
                } else {
                    getRecommendationsCulinary(location.latitude, location.longitude)
                }
            }
        } else {
            searchCulinary(query)
        }
    }

    private fun fetchAllCulinary() {
        viewModel.getAllCulinary().observe(this@MainActivity) { result ->
            handleResult(result)
        }
    }

    private fun searchCulinary(query: String) {
        viewModel.searchCulinary(query).observe(this@MainActivity) { result ->
            handleResult(result)
        }
    }

    private fun getRecommendationsCulinary(lat: Double, lon: Double) {
        viewModel.getRecommendationsCulinary(lat, lon).observe(this@MainActivity) { result ->
            handleResult(result)
        }
    }

    private fun handleResult(result: Result<List<CulinaryResponseItem>>) {
        when (result) {
            is Result.Loading -> {
                if (!binding.swipeRefresh.isRefreshing) {
                    binding.progressBar.visibility = VISIBLE
                }
            }
            is Result.Success -> {
                if (binding.swipeRefresh.isRefreshing) {
                    binding.swipeRefresh.isRefreshing = false
                } else {
                    binding.progressBar.visibility = GONE
                }
                val culinaryData = result.data
                culinaryAdapter.submitList(culinaryData)
                updateEmptyView(culinaryData)
            }
            is Result.Error -> {
                if (binding.swipeRefresh.isRefreshing) {
                    binding.swipeRefresh.isRefreshing = false
                } else {
                    binding.progressBar.visibility = GONE
                }
                Toast.makeText(this, getString(R.string.failed_get_data, result.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
     * Search
     */

    private fun initSearchBar() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                performFetchBasedOnState()
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
                            val settingsIntent = Intent(this@MainActivity, SettingActivity::class.java)
                            startActivity(settingsIntent)
                            true
                        }
                        else -> false
                    }
                }
            })
        }
    }

    private fun updateEmptyView(data: List<CulinaryResponseItem>) {
        if (binding.searchBar.text.isNotEmpty()) {
            binding.noData.visibility = if (data.isEmpty()) VISIBLE else GONE
        } else {
            binding.noRecommendation.visibility = if (data.isEmpty()) VISIBLE else GONE
        }
    }

    /*
     * Location
     */

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                binding.tvLocation.text = getString(R.string.location_permission_denied)
                Toast.makeText(this, getString(R.string.location_need_permission), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.getLastKnownLocation { location: Location? ->
                location?.let {
                    getAddressFromLocation(it.latitude, it.longitude)
                } ?: run {
                    Toast.makeText(this@MainActivity, getString(R.string.location_not_found), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val locationName = address.getAddressLine(0)
                    withContext(Dispatchers.Main) {
                        binding.tvLocation.text = locationName
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.tvLocation.text = getString(R.string.location_error_name, e.message)
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.location_error_name, e.message),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun refreshData() {
        binding.swipeRefresh.setOnRefreshListener {
            getLocation()
            performFetchBasedOnState()
        }
    }

    /*
     * Food Scan
     */

    private fun initFoodScan() {
        binding.fabScanfood.setOnClickListener {
            val intent = Intent(this, FoodScanActivity::class.java)
            startActivity(intent)
        }
    }
}
