package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetroServiceInterface {

    @POST("/user/login")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun login(@Body params: LoginDTO): Call<LoginResponseDTO>
}