package com.example.imjaewook_qni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.load

class MainActivity : AppCompatActivity() {

    // https://quickchart.io/wordcloud?format=png&text=Four score and seven years ago...

    lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image_view)

        image.load("https://quickchart.io/wordcloud?format=png&text=Four score and seven years ago...")
    }
}