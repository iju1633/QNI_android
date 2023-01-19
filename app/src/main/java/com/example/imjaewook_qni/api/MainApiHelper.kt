package com.example.imjaewook_qni.api

import com.example.imjaewook_qni.api.dto.AnswerDTO
import com.example.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.example.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.example.imjaewook_qni.api.dto.AnswerUpdateDTO
import retrofit2.Response

interface MainApiHelper {

    suspend fun getQuestionList(userId: String): Response<List<QuestionAnswerDTO>>
    suspend fun getAnsweredQuestionList(userId: String): Response<List<AnsweredQuestionDTO>>
    suspend fun saveAnswer(answerDTO: AnswerDTO): Response<Void>
    suspend fun updateAnswer(answerUpdateDTO: AnswerUpdateDTO): Response<Void>
}