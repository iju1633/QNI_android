package com.example.imjaewook_qni.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.databinding.ActivityWordcloudBinding
import com.example.imjaewook_qni.ui.viewmodel.WordCloudViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordCloudActivity : AppCompatActivity() {

    private lateinit var activityWordCloudBinding: ActivityWordcloudBinding
    lateinit var viewModel: WordCloudViewModel

    private val baseURL = "https://quickchart.io/wordcloud?format=png&text="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityWordCloudBinding = ActivityWordcloudBinding.inflate(layoutInflater)
        setContentView(activityWordCloudBinding.root)

        setUpViewModel()

        viewModel.getCombinedAnswer(ImJaeWookQniApplication.prefs.getString("userId", "0"))
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
}