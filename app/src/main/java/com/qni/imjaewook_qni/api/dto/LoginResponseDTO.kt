package com.qni.imjaewook_qni.api.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(

    @SerializedName("userId")
    val userId: String?,
    @SerializedName("nickname")
    val nickname: String?
)