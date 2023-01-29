package com.qni.imjaewook_qni.api.dto

import com.google.gson.annotations.SerializedName

data class NicknameResponseDTO(

    @SerializedName("newNickname")
    val newNickname: String
)