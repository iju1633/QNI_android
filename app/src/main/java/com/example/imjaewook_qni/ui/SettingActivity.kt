package com.example.imjaewook_qni.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}