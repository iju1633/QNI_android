package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.AnswerDTO
import com.example.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.example.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.example.imjaewook_qni.api.dto.AnswerUpdateDTO
import retrofit2.Response
import retrofit2.http.*

interface MainApiService {

    @FormUrlEncoded
    @GET("/question/list/userId/{userId}")
    suspend fun getUserQuestions(
        @Path(value = "userId", encoded = true) userId : String
    ) : Response<List<QuestionAnswerDTO>>

    @FormUrlEncoded
    @GET("/question/answered/list/userId/{userId}")
    suspend fun getUserAnsweredQuestions(
        @Path(value = "userId", encoded = true) userId : String
    ) : Response<List<AnsweredQuestionDTO>>

    @POST("/answer/confirm")
    suspend fun saveAnswer(@Body answerDTO: AnswerDTO) : Response<Void>

    @PATCH("/answer/update")
    suspend fun updateAnswer(@Body answerUpdateDTO: AnswerUpdateDTO) : Response<Void>
}