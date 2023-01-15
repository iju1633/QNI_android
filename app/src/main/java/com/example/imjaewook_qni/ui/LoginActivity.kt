package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.RetrofitInstance

import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUI()
        setUpViewModel()
    }

    private fun setUpViewModel() {

        loginViewModel.loginResponseDTOLiveData.observe(this) { response ->
            response?.let {

                if (it.isSuccessful) {

                    response.body()?.let { it1 -> Log.v("로그인된 유저의 id : ", it1.userId) }
                    response.body()?.let { it1 ->
                        Log.v(
                            "로그인된 유저의 nickname : ",
                            it1.nickname
                        )
                    }
//                    response.body()
//                        ?.let { it1 -> ImJaeWookQniApplication.prefs.setString("userId", it1.userId) }
//                    response.body()
//                        ?.let { it1 -> ImJaeWookQniApplication.prefs.setString("nickname", it1.nickname) }

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    // log
                    Log.v("로그인된 유저의 id : ", ImJaeWookQniApplication.prefs.getString("userId", "no userId"))
                    Log.v(
                        "로그인된 유저의 nickname : ",
                        ImJaeWookQniApplication.prefs.getString("nickname", "no nickname")
                    )
                } else
                    Toast.makeText(this@LoginActivity, "Login Failed !!", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun setUpUI() {

        setUpViewBinding()

        activityLoginBinding.buttonLogin.setOnClickListener {

            val uid = activityLoginBinding.editTextUid.text.toString()
            val pwd = activityLoginBinding.editTextPassword.text.toString()

            if (uid.isEmpty()) {
                activityLoginBinding.editTextUid.error = "User ID required"
                activityLoginBinding.editTextUid.requestFocus()
                return@setOnClickListener
            }

            if (pwd.isEmpty()) {
                activityLoginBinding.editTextPassword.error = "Password required"
                activityLoginBinding.editTextPassword.requestFocus()
                return@setOnClickListener
            }

            login()
        }

        activityLoginBinding.textViewRegister.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        activityLoginBinding.textViewRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpViewBinding() {
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
    }

    private fun login() {

        val uid = activityLoginBinding.editTextUid.text.toString()
        val pwd = activityLoginBinding.editTextPassword.text.toString()

        val loginDTO = LoginDTO(
            uid,
            pwd
        )

        loginViewModel.userLogin(loginDTO)
    }
}


