package com.example.imjaewook_qni.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.R
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.ui.viewmodel.LoginUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginTestActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding : ActivityLoginBinding
    lateinit var viewModel: LoginUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        initViewModel()
        activityLoginBinding.buttonLogin.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val uid = activityLoginBinding.editTextUid.text.toString()
        val pwd = activityLoginBinding.editTextPassword.text.toString()

        val loginDTO = LoginDTO(uid, pwd)
        viewModel.login(loginDTO)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LoginUserViewModel::class.java)
        viewModel.getCreateNewUserObserver().observe(this, Observer <LoginResponseDTO?>{

            if(it  == null) {
                Toast.makeText(this@LoginTestActivity, "Failed to login", Toast.LENGTH_LONG).show()
            } else {

                Log.v("로그인한 유저의 id : ", ImJaeWookQniApplication.userId)
                Log.v("로그인한 유저의 nickname : ", ImJaeWookQniApplication.nickname)
                Toast.makeText(this@LoginTestActivity, "Successfully Login !!", Toast.LENGTH_LONG).show()
            }
        })
    }
}