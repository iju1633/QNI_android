package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        activityLoginBinding.buttonLogin.setOnClickListener {

            val uid = activityLoginBinding.editTextUid.text.toString()
            val pwd = activityLoginBinding.editTextPassword.text.toString()

            val intent = Intent(this, LoginLoadingActivity::class.java)
            intent.putExtra("uid", uid)
            intent.putExtra("pwd", pwd)
            startActivity(intent)

        }

        activityLoginBinding.textViewRegister.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        activityLoginBinding.textViewRegister.setOnClickListener {

            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}