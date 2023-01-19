package com.example.imjaewook_qni.ui.repository

import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Response

interface UserRepository {

    suspend fun userRegister(registerDTO: RegisterDTO) : Response<Void>
}