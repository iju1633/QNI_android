package com.example.imjaewook_qni.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.databinding.ActivitySplashBinding
import com.example.imjaewook_qni.ui.viewmodel.LoginUserViewModel
import com.example.imjaewook_qni.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding : ActivityLoginBinding
    private lateinit var activitySplashActivity : ActivitySplashBinding
    lateinit var viewModel: LoginUserViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        activitySplashActivity = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(activitySplashActivity.root)

        setUpViewModel()
        login()
        startLoading()
    }

    private fun startLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, DURATION)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(LoginUserViewModel::class.java)
        viewModel.loginUserObserver().observe(this) {

            if (it == null) {
                Toast.makeText(this@LoadingActivity, "Failed to login", Toast.LENGTH_LONG).show()
            } else {
                ImJaeWookQniApplication.prefs.setString("userId", it.userId.toString())
                ImJaeWookQniApplication.prefs.setString("nickname", it.nickname.toString())

                Toast.makeText(this@LoadingActivity, "Successfully Login !!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        mainViewModel.getAnsweredUserQuestionList(ImJaeWookQniApplication.prefs.getString("userId", "0").toLong())
        mainViewModel.getUserQuestionList(ImJaeWookQniApplication.prefs.getString("userId", "0").toLong())
    }

    private fun login() {
        val uid = intent.getStringExtra("uid").toString()
        val pwd = intent.getStringExtra("pwd").toString()

        val loginDTO = LoginDTO(uid, pwd)
        viewModel.login(loginDTO)
    }

    companion object { // 2초 간 보여짐
        private const val DURATION: Long = 2000
    }
}