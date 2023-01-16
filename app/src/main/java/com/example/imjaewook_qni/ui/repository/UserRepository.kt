package com.example.imjaewook_qni.ui.repository

import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Call
import retrofit2.Response

interface UserRepository {

    suspend fun userLogin(loginDTO: LoginDTO) : Response<LoginResponseDTO>
    suspend fun userRegister(registerDTO: RegisterDTO) : Response<Void>
}