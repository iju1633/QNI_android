package com.example.imjaewook_qni.api.dto

import com.google.gson.annotations.SerializedName

data class NicknameDTO(

    @SerializedName("nickname")
    val nickname : String,
    @SerializedName("userId")
    val userId : String
)