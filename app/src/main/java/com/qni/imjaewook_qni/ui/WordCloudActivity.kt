package com.qni.imjaewook_qni.ui

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.qni.imjaewook_qni.ImJaeWookQniApplication
import com.qni.imjaewook_qni.databinding.ActivityWordcloudBinding
import com.qni.imjaewook_qni.ui.viewmodel.WordCloudViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

@AndroidEntryPoint
class WordCloudActivity : AppCompatActivity() {
    // TODO: 권한 요청

    private lateinit var activityWordCloudBinding: ActivityWordcloudBinding
    lateinit var viewModel: WordCloudViewModel

    private val baseURL = "https://quickchart.io/wordcloud?format=png&text="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWordCloudBinding = ActivityWordcloudBinding.inflate(layoutInflater)
        setContentView(activityWordCloudBinding.root)

        setUpUI()
        setUpViewModel()

        viewModel.getCombinedAnswer(ImJaeWookQniApplication.prefs.getString("userId", "0"))


    }

    private fun setUpUI() {
        activityWordCloudBinding.backToMain.setOnClickListener {

            finish()

            val intent = Intent(this@WordCloudActivity, MainActivity::class.java)
            startActivity(intent)
        }

        activityWordCloudBinding.saveToGallery.setOnClickListener {

            val bitmap = getImageOfView(activityWordCloudBinding.wordCloud)
            if (bitmap != null) {
                saveToGallery(bitmap)
            }
        }
    }

    private fun saveToGallery(bitmap: Bitmap) {

        checkPermission()

        val imageName = "QNI_${System.currentTimeMillis()}.png"
        var fos : OutputStream? = null
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri : Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let {
                    resolver.openOutputStream(it)
                }
            }
        } else {
            val imageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imageDirectory, imageName)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            Toast.makeText(
                this@WordCloudActivity,
                "Successfully saved the image !!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun getImageOfView(view: ImageView): Bitmap? {
        var image : Bitmap? = null
        try {
            image = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(image)
            view.draw(canvas)
        } catch (e: Exception) {
            Log.e("GETTING_IMAGE_OF_IMAGEVIEW", "Cannot Capture the image from word cloud view.")
        }

        return image
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(WordCloudViewModel::class.java)
        viewModel.wordCloudObserver().observe(this) {

            if (it == null) {
                Toast.makeText(
                    this@WordCloudActivity,
                    "Failed to bring combined answer !!",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                activityWordCloudBinding.wordCloud.load(baseURL + it.combinedAnswer)

                Toast.makeText(
                    this@WordCloudActivity,
                    "Successfully brought combined answer !!",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun checkPermission() {
        val permission = mutableMapOf<String, String>()
        permission["storageRead"] = Manifest.permission.READ_EXTERNAL_STORAGE
        permission["storageWrite"] =  Manifest.permission.WRITE_EXTERNAL_STORAGE

        // 현재 권한 상태 검사
        val denied = permission.count { ContextCompat.checkSelfPermission(this, it.value)  == PackageManager.PERMISSION_DENIED }

        // 마시멜로 버전 이후
        if(denied > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission.values.toTypedArray(), 1)
        }
    }
}