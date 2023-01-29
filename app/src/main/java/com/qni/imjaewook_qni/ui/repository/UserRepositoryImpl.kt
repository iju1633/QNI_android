package com.qni.imjaewook_qni.ui.repository

import com.qni.imjaewook_qni.api.UserApiHelper
import com.qni.imjaewook_qni.api.dto.RegisterDTO
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteSource: UserApiHelper
) : UserRepository {

    override suspend fun userRegister(registerDTO: RegisterDTO): Response<Void> =
        remoteSource.userRegister(registerDTO)
}