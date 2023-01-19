package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    @POST("/user/register")
    suspend fun userRegister(@Body register: RegisterDTO): Response<Void>
}