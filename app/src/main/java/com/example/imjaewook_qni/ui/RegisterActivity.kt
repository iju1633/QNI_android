package com.example.imjaewook_qni.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.api.dto.RegisterDTO
import com.example.imjaewook_qni.databinding.ActivityRegisterBinding
import com.example.imjaewook_qni.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUI()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        registerViewModel.registerResponseLiveData.observe(this) { response ->
            response?.let {
                if (it.isSuccessful) {

                    Toast.makeText(this@RegisterActivity, "Register Success !!", Toast.LENGTH_SHORT)
                        .show()

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else
                    Toast.makeText(this@RegisterActivity, "Register Failed !!", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun setUpUI() {

        setUpViewBinding()

        activityRegisterBinding.buttonRegister.setOnClickListener {

            val nickname = activityRegisterBinding.editTextNickname.text.toString()
            val uid = activityRegisterBinding.editTextUid.text.toString()
            val pwd = activityRegisterBinding.editTextPassword.text.toString()

            if (nickname.isEmpty()) {
                activityRegisterBinding.editTextNickname.error = "Nickname required"
                activityRegisterBinding.editTextNickname.requestFocus()
                return@setOnClickListener
            }

            if (uid.isEmpty()) {
                activityRegisterBinding.editTextUid.error = "User ID required"
                activityRegisterBinding.editTextUid.requestFocus()
                return@setOnClickListener
            }

            if (pwd.isEmpty()) {
                activityRegisterBinding.editTextPassword.error = "Password required"
                activityRegisterBinding.editTextPassword.requestFocus()
                return@setOnClickListener
            }

            register()
        }
    }

    private fun setUpViewBinding() {
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)
    }

    private fun register() {

        val nickname = activityRegisterBinding.editTextNickname.text.toString()
        val uid = activityRegisterBinding.editTextUid.text.toString()
        val pwd = activityRegisterBinding.editTextPassword.text.toString()

        val registerDTO = RegisterDTO(
            nickname,
            uid,
            pwd
        )

        registerViewModel.userRegister(registerDTO)
    }
}