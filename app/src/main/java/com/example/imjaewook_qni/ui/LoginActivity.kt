package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.imjaewook_qni.api.RetrofitInstance
import com.example.imjaewook_qni.api.dto.LoginRequest
import com.example.imjaewook_qni.api.dto.LoginResponse
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        buttonLogin.setOnClickListener {

            val uid = editTextUid.text.toString().trim()
            val pwd = editTextPassword.text.toString().trim()

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

            login();
        }

        textViewRegister.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        textViewRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent);
        }
    }

    private fun login() {

        val uid = binding.editTextUid.text.toString()
        val pwd = binding.editTextPassword.text.toString()

        val loginRequest = LoginRequest(
            uid,
            pwd
        )

        RetrofitInstance.api.userLogin(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) { // TODO : 에러체크 필요 ! 로그인이 다 되고 있음
                    val data = response.body()?.component1() // GsonConverter를 사용해 데이터매핑
                    if (data != null) {
                        Log.v("로그인 성공!!! 아이디는  : ", data)
                    }
                    Toast.makeText(this@LoginActivity, "Login Success !!", Toast.LENGTH_SHORT).show()

                    // sharedPreference에 nickname 저장
                    val sharedPreference = getSharedPreferences("shared", 0) // Private 모드
                    val editor = sharedPreference.edit()
                    editor.putString("nickname", data)
                    editor.apply()

                    sharedPreference.getString("nickname", "")?.let { Log.v("저장된 아이디 값 불러오기 : ", it) }

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else
                    Toast.makeText(this@LoginActivity, "Login Failed !!", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.v("서버 통신 실패 !!!", "서버통신에 실패하였습니다.")
                Toast.makeText(this@LoginActivity, "Login Failed !!", Toast.LENGTH_SHORT).show()
            }
        })
    }


}



