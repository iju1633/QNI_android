package com.example.imjaewook_qni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.load

class MainActivity : AppCompatActivity() {

    private val baseURL = "https://quickchart.io/wordcloud?format=png&text="

    lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image_view)

        image.load(baseURL + "Four score and seven years ago...")
    }
}