package com.example.imjaewook_qni.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.R
import com.example.imjaewook_qni.api.RetrofitInstance
import com.example.imjaewook_qni.api.dto.DefaultResponse
import kotlinx.android.synthetic.main.activity_login.buttonLogin
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.editTextUid
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonLogin.setOnClickListener {

            val nickname = editTextNickname.text.toString().trim()
            val uid = editTextUid.text.toString().trim()
            val pwd = editTextPassword.text.toString().trim()

            if (nickname.isEmpty()) {
                editTextUid.error = "Nickname required"
                editTextUid.requestFocus()
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

//            RetrofitInstance.retrofit.userRegister(nickname, uid, pwd)
//                .enqueue(object : Callback<DefaultResponse> {
//                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onResponse(
//                        call: Call<DefaultResponse>,
//                        response: Response<DefaultResponse>
//                    ) {
//                        if (!response.body()?.error!!) {
//
//                            val intent = Intent(applicationContext, MainActivity::class.java)
//                            intent.flags =
//                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//
//                            startActivity(intent)
//
//
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                response.body()?.message,
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }
//
//                    }
//                })

        }
    }
}