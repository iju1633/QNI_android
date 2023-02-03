package com.qni.imjaewook_qni.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.qni.imjaewook_qni.databinding.ActivitySplashBinding
import com.qni.imjaewook_qni.util.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordCloudLoadingActivity : AppCompatActivity() {

    private lateinit var activitySplashActivity: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySplashActivity = ActivitySplashBinding.inflate(layoutInflater)

        checkPermission()

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