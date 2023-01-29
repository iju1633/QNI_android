package com.qni.imjaewook_qni.api

import com.qni.imjaewook_qni.api.dto.*
import retrofit2.Call
import retrofit2.http.*

interface RetroServiceInterface {

    @POST("/user/login")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun login(@Body params: LoginDTO): Call<LoginResponseDTO>

    @POST("/user/logout")
    fun logout(): Call<Void>

    @PATCH("/setting/user/nickname")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun changeNickname(@Body params: NicknameDTO): Call<NicknameResponseDTO>

    @DELETE("/setting/withdrawal/userId/{userId}")
    fun withdrawalUser(@Path(value = "userId", encoded = true) userId: String): Call<Void>

    @GET("/wordCloud/userId/{userId}")
    fun getCombinedAnswer(
        @Path(
            value = "userId",
            encoded = true
        ) userId: String
    ): Call<CombinedAnswerDTO>
}