package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.ui.viewmodel.LoginViewModel
import com.example.imjaewook_qni.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding : ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        //setUpViewModel()
        activityLoginBinding.buttonLogin.setOnClickListener {

            val uid = activityLoginBinding.editTextUid.text.toString()
            val pwd = activityLoginBinding.editTextPassword.text.toString()
//
//            if (uid.isEmpty()) {
//                activityLoginBinding.editTextUid.error = "User Id required"
//                activityLoginBinding.editTextUid.requestFocus()
//                return@setOnClickListener
//            }
//
//            if (pwd.isEmpty()) {
//                activityLoginBinding.editTextPassword.error = "Password required"
//                activityLoginBinding.editTextPassword.requestFocus()
//                return@setOnClickListener
//            }

            val intent = Intent(this, LoginLoadingActivity::class.java)
            intent.putExtra("uid", uid)
            intent.putExtra("pwd", pwd)
            startActivity(intent)

            //login()
        }

        activityLoginBinding.textViewRegister.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        activityLoginBinding.textViewRegister.setOnClickListener {

            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val uid = activityLoginBinding.editTextUid.text.toString()
        val pwd = activityLoginBinding.editTextPassword.text.toString()

        val loginDTO = LoginDTO(uid, pwd)
        viewModel.login(loginDTO)

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.loginUserObserver().observe(this) {

            if (it == null) {
                Toast.makeText(this@LoginActivity, "Failed to login", Toast.LENGTH_LONG).show()
            } else {

                Toast.makeText(this@LoginActivity, "Successfully Login !!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}