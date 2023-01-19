package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Response

interface UserApiHelper {

    suspend fun userRegister(registerDTO: RegisterDTO): Response<Void>
}