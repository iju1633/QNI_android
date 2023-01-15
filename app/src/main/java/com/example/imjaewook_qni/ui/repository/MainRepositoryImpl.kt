package com.example.imjaewook_qni.ui.repository

import com.example.imjaewook_qni.api.MainApiHelper
import com.example.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.example.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.example.imjaewook_qni.api.dto.AnswerDTO
import com.example.imjaewook_qni.api.dto.AnswerUpdateDTO
import retrofit2.Response
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val remoteSource: MainApiHelper
) : MainRepository {
    override suspend fun getQuestionList(userId: String): Response<List<QuestionAnswerDTO>> =
        remoteSource.getQuestionList(userId)

    override suspend fun getAnsweredQuestionList(userId: String): Response<List<AnsweredQuestionDTO>> =
        remoteSource.getAnsweredQuestionList(userId)

    override suspend fun saveAnswer(answerDTO: AnswerDTO): Response<Void> =
        remoteSource.saveAnswer(answerDTO)

    override suspend fun updateAnswer(answerUpdateDTO: AnswerUpdateDTO): Response<Void> =
        remoteSource.updateAnswer(answerUpdateDTO)
}