package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiService {

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/user/login")
    fun userLogin(@Body login: LoginDTO): Response<LoginResponseDTO>

    @POST("/user/register")
    suspend fun userRegister(@Body register : RegisterDTO): Response<Void>
}