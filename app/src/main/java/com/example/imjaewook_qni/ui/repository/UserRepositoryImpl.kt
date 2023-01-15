package com.example.imjaewook_qni.ui.repository

import com.example.imjaewook_qni.api.UserApiHelper
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteSource: UserApiHelper
) : UserRepository {

    override suspend fun userLogin(loginDTO: LoginDTO): Response<LoginResponseDTO> =
        remoteSource.userLogin(loginDTO)

    override suspend fun userRegister(registerDTO: RegisterDTO): Response<Void> =
        remoteSource.userRegister(registerDTO)
}