package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imjaewook_qni.R
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        logout.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        settingButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intent);
        }

        wordCloudButton.setOnClickListener {
            val intent = Intent(this@MainActivity, WordCloudActivity::class.java)
            startActivity(intent);
        }
    }
}