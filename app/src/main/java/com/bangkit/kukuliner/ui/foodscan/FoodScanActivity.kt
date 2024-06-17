package com.bangkit.kukuliner.ui.foodscan

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bangkit.kukuliner.R
import com.bangkit.kukuliner.databinding.ActivityFoodScanBinding
import com.bangkit.kukuliner.helper.ImageClassifierHelper
import com.bangkit.kukuliner.ui.foodscan.CameraActivity.Companion.CAMERAX_RESULT
import com.bangkit.kukuliner.ui.resultscan.ResultScanActivity
import org.tensorflow.lite.task.vision.classifier.Classifications

class FoodScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodScanBinding
    private var currentImageUri: Uri = "currentImageUri".toUri()
    private var label_image: String = "label_imagae"
    private var score_image: String = "score_image"

    private val requestPermissionLauncherFileX =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_scan)

        binding = ActivityFoodScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        allPermissionsGranted()
        setupAction()

        binding.fabBack.setOnClickListener {
            finish()
        }

    }

    private fun setupAction() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncherFileX.launch(REQUIRED_PERMISSION)
        }
        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnScan.setOnClickListener { analyzeImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Toast.makeText(this, "Foto belum dipilih", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)!!.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.imgPhotoFood.setImageURI(it)
        }
    }

    private fun analyzeImage() {

        if(currentImageUri == "currentImageUri".toUri()){
            Toast.makeText(this, "Foto belum dipilih", Toast.LENGTH_SHORT).show()
            return
        }

        val imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Log.d(TAG, "ShowImage: $error")
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    results?.let {
                        val result = it[0]
                        val label = result.categories[0].label
                        val score = result.categories[0].score

                        fun Float.formatToString(): String {
                            return String.format("%.2f%%", this * 100)
                        }

                        label_image = label
                        score_image = score.formatToString()
                    }
                }
            }
        )

        imageClassifierHelper.classifyStaticImage(currentImageUri)
        val intent = Intent(this, ResultScanActivity::class.java)
        intent.putExtra(IMAGE_CLASS, currentImageUri.toString())
        intent.putExtra(LABEL, label_image)
        intent.putExtra(SCORE, score_image)
        startActivity(intent)
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val IMAGE_CLASS = "IMAGE_CLASS"
        const val LABEL = "LABEL"
        const val SCORE = "SCORE"
    }
}