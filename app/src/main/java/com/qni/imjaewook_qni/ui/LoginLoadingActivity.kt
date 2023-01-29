package com.qni.imjaewook_qni.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.qni.imjaewook_qni.ImJaeWookQniApplication
import com.qni.imjaewook_qni.api.dto.LoginDTO
import com.qni.imjaewook_qni.databinding.ActivityLoginBinding
import com.qni.imjaewook_qni.databinding.ActivitySplashBinding
import com.qni.imjaewook_qni.ui.viewmodel.LoginViewModel
import com.qni.imjaewook_qni.ui.viewmodel.MainViewModel
import com.qni.imjaewook_qni.util.Const
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginLoadingActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var activitySplashActivity: ActivitySplashBinding
    lateinit var viewModel: LoginViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        activitySplashActivity = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(activitySplashActivity.root)

        setUpViewModel()
        login()
    }

    private fun startLoading() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, Const.LOADING_DURATION)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.loginUserObserver().observe(this) {

            if (it == null) {

                Toast.makeText(this@LoginLoadingActivity, "Failed to login", Toast.LENGTH_LONG)
                    .show()

                finish()
            } else {
                ImJaeWookQniApplication.prefs.setString("userId", it.userId.toString())
                ImJaeWookQniApplication.prefs.setString("nickname", it.nickname.toString())

                startLoading()

                Toast.makeText(
                    this@LoginLoadingActivity,
                    "Successfully Login !!",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        mainViewModel.getAnsweredUserQuestionList(
            ImJaeWookQniApplication.prefs.getString(
                "userId",
                "0"
            ).toLong()
        )
        mainViewModel.getUserQuestionList(
            ImJaeWookQniApplication.prefs.getString("userId", "0").toLong()
        )
    }

    private fun login() {
        val uid = intent.getStringExtra("uid").toString()
        val pwd = intent.getStringExtra("pwd").toString()

        val loginDTO = LoginDTO(uid, pwd)
        viewModel.login(loginDTO)
    }
}