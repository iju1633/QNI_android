package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.ui.viewmodel.LoginUserViewModel
import com.example.imjaewook_qni.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginTestActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding : ActivityLoginBinding
    lateinit var viewModel: LoginUserViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        setUpViewModel()
        activityLoginBinding.buttonLogin.setOnClickListener {

            val uid = activityLoginBinding.editTextUid.text.toString()
            val pwd = activityLoginBinding.editTextPassword.text.toString()

            if (uid.isEmpty()) {
                activityLoginBinding.editTextUid.error = "User Id required"
                activityLoginBinding.editTextUid.requestFocus()
                return@setOnClickListener
            }

            if (pwd.isEmpty()) {
                activityLoginBinding.editTextPassword.error = "Password required"
                activityLoginBinding.editTextPassword.requestFocus()
                return@setOnClickListener
            }

            login()

            mainViewModel.getAnsweredUserQuestionList(ImJaeWookQniApplication.prefs.getString("userId", "0").toLong())
            mainViewModel.getUserQuestionList(ImJaeWookQniApplication.prefs.getString("userId", "0").toLong())
        }

        activityLoginBinding.textViewRegister.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        activityLoginBinding.textViewRegister.setOnClickListener {

            val intent = Intent(this@LoginTestActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val uid = activityLoginBinding.editTextUid.text.toString()
        val pwd = activityLoginBinding.editTextPassword.text.toString()

        val loginDTO = LoginDTO(uid, pwd)
        viewModel.login(loginDTO)

        val intent = Intent(this@LoginTestActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(LoginUserViewModel::class.java)
        viewModel.loginUserObserver().observe(this) {

            if (it == null) {
                Toast.makeText(this@LoginTestActivity, "Failed to login", Toast.LENGTH_LONG).show()
            } else {

                Toast.makeText(this@LoginTestActivity, "Successfully Login !!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}