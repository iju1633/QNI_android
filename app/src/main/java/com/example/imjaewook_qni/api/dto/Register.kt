package com.example.imjaewook_qni.api.dto

import com.google.gson.annotations.SerializedName

data class Register(

    @SerializedName("nickname")
    private val nickname: String,
    @SerializedName("uid")
    private val uid: String,
    @SerializedName("pwd")
    private val pwd: String
)
