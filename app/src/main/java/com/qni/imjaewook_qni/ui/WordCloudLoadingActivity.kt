package com.qni.imjaewook_qni.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.qni.imjaewook_qni.databinding.ActivitySplashBinding
import com.qni.imjaewook_qni.util.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordCloudLoadingActivity : AppCompatActivity() {

    private lateinit var activitySplashActivity: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySplashActivity = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(activitySplashActivity.root)

        startLoading()
    }

    private fun startLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, WordCloudActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, Const.LOADING_DURATION)
    }
}