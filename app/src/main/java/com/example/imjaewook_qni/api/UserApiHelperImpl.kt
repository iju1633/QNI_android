package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Response
import javax.inject.Inject

class UserApiHelperImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserApiHelper {

    override suspend fun userRegister(registerDTO: RegisterDTO): Response<Void> =
        userApiService.userRegister(registerDTO)
}