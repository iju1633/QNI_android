package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Call
import retrofit2.Response

interface UserApiHelper {

//    suspend fun userLogin(loginDTO: LoginDTO) : Response<LoginResponseDTO>
//    fun loginUser(loginDTO: LoginDTO) : Call<LoginResponseDTO>
    suspend fun userRegister(registerDTO: RegisterDTO) : Response<Void>
}