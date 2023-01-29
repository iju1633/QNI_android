package com.qni.imjaewook_qni.api

import com.qni.imjaewook_qni.api.dto.AnswerDTO
import com.qni.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.qni.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.qni.imjaewook_qni.api.dto.AnswerUpdateDTO
import retrofit2.Response
import javax.inject.Inject

class MainApiHelperImpl @Inject constructor(
    private val mainApiService: MainApiService
) : MainApiHelper {

    override suspend fun getQuestionList(userId: String): Response<List<QuestionAnswerDTO>> =
        mainApiService.getUserQuestions(userId)

    override suspend fun getAnsweredQuestionList(userId: String): Response<List<AnsweredQuestionDTO>> =
        mainApiService.getUserAnsweredQuestions(userId)

    override suspend fun saveAnswer(answerDTO: AnswerDTO): Response<Void> =
        mainApiService.saveAnswer(answerDTO)

    override suspend fun updateAnswer(answerUpdateDTO: AnswerUpdateDTO): Response<Void> =
        mainApiService.updateAnswer(answerUpdateDTO)
}