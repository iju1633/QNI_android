package com.example.imjaewook_qni

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.imjaewook_qni.util.PreferenceUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ImJaeWookQniApplication : Application(), Configuration.Provider {

    companion object {
        lateinit var prefs: PreferenceUtil
        var answeredQuestionIdList = ArrayList<String>()
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}