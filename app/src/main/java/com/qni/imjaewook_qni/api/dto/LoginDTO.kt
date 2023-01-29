package com.qni.imjaewook_qni.api.dto

import com.google.gson.annotations.SerializedName

data class LoginDTO(

    @SerializedName("uid")
    val uid: String,
    @SerializedName("pwd")
    val pwd: String
)

