package com.qni.imjaewook_qni.ui.repository

import com.qni.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.qni.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.qni.imjaewook_qni.api.dto.AnswerDTO
import com.qni.imjaewook_qni.api.dto.AnswerUpdateDTO
import retrofit2.Response

interface MainRepository {

    suspend fun getQuestionList(userId: String): Response<List<QuestionAnswerDTO>>
    suspend fun getAnsweredQuestionList(userId: String): Response<List<AnsweredQuestionDTO>>
    suspend fun saveAnswer(answerDTO: AnswerDTO): Response<Void>
    suspend fun updateAnswer(answerUpdateDTO: AnswerUpdateDTO): Response<Void>
}