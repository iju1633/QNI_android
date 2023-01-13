package com.example.imjaewook_qni.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.api.RetrofitInstance
import com.example.imjaewook_qni.api.dto.RegisterRequest
import com.example.imjaewook_qni.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_login.buttonLogin
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.editTextUid
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        buttonLogin.setOnClickListener {

            val nickname = editTextNickname.text.toString().trim()
            val uid = editTextUid.text.toString().trim()
            val pwd = editTextPassword.text.toString().trim()

            if (nickname.isEmpty()) {
                editTextNickname.error = "Nickname required"
                editTextNickname.requestFocus()
                return@setOnClickListener
            }

            if (uid.isEmpty()) {
                editTextUid.error = "User ID required"
                editTextUid.requestFocus()
                return@setOnClickListener
            }

            if (pwd.isEmpty()) {
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            register();
        }
    }

    private fun register() {

        val nickname = binding.editTextNickname.text.toString()
        val uid = binding.editTextUid.text.toString()
        val pwd = binding.editTextPassword.text.toString()

        val registerRequest = RegisterRequest(
            nickname,
            uid,
            pwd
        )

        RetrofitInstance.api.userRegister(registerRequest).enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    Log.d("test", response.body().toString())
                    val data = response.body().toString()
                    Log.v("회원가입 성공!!!", data)
                    Toast.makeText(this@RegisterActivity, "Register Success !!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else
                    Toast.makeText(this@RegisterActivity, "Register Failed !!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.v("서버 통신 실패 !!!", "서버통신에 실패하였습니다.")
                Toast.makeText(this@RegisterActivity, "Register Failed !!", Toast.LENGTH_SHORT).show()
            }
        })
    }


}