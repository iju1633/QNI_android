package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.LoginRequest
import com.example.imjaewook_qni.api.dto.LoginResponse
import com.example.imjaewook_qni.api.dto.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/user/login")
    fun userLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/user/register")
    fun userRegister(@Body register : RegisterRequest): Call<Void>
}