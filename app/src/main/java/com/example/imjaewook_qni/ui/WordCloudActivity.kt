package com.example.imjaewook_qni.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.imjaewook_qni.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordCloudActivity : AppCompatActivity() {

    private val baseURL = "https://quickchart.io/wordcloud?format=png&text="

    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordcloud)

        image = findViewById(R.id.wordCloud)

        image.load(baseURL + "I would choose love. I used to live in Philippines for education when I was young like 13. I love to sing love love love which is Roy kim's song.")
    }
}