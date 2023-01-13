package com.example.imjaewook_qni.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.imjaewook_qni.R

class WordCloudActivity : AppCompatActivity() {

    private val baseURL = "https://quickchart.io/wordcloud?format=png&text="

    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordcloud)

        image = findViewById(R.id.wordCloud)

        image.load(baseURL + "Four score and seven years ago...")
    }
}