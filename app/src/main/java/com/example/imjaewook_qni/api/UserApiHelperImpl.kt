package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class UserApiHelperImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserApiHelper {
//    override suspend fun userLogin(loginDTO: LoginDTO): Response<LoginResponseDTO> =
//        userApiService.userLogin(loginDTO)
//
//    override fun loginUser(loginDTO: LoginDTO): Call<LoginResponseDTO> =
//        userApiService.loginUser(loginDTO)

    override suspend fun userRegister(registerDTO: RegisterDTO): Response<Void> =
        userApiService.userRegister(registerDTO)
}