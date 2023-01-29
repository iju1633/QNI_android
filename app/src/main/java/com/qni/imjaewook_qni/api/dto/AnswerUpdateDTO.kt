package com.qni.imjaewook_qni.api.dto

data class AnswerUpdateDTO(

    val newAnswer: String,
    val questionId: String,
    val userId: Long
)
