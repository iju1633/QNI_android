package com.example.imjaewook_qni.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.api.dto.AnswerDTO
import com.example.imjaewook_qni.databinding.ActivityMainBinding
import com.example.imjaewook_qni.databinding.ActivityUpdateAnswerBinding
import com.example.imjaewook_qni.ui.viewmodel.AnswerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerUpdateActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var activityUpdateAnswerBinding: ActivityUpdateAnswerBinding

    private val answerViewModel : AnswerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUI()
        setUpViewModel()
    }

    override fun onStart() {
        super.onStart()

        answerViewModel.getUserQuestionList(3L)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViewModel() {

        answerViewModel.answeredQuestionDTOLiveData.observe(this) { answeredQuestion ->

            val secondIntent = intent
            val chosenQuestionId = secondIntent.getIntExtra("ChosenAnsweredQuestionId", 0)

            activityUpdateAnswerBinding.question.text = answeredQuestion[chosenQuestionId].questionId.toString() + ". " + answeredQuestion[chosenQuestionId].question
            activityUpdateAnswerBinding.answerBox.text = answeredQuestion[chosenQuestionId].answer
        }
    }

    private fun setUpUI() {
        setUpViewBinding()

        val answerDTO = AnswerDTO("", 0L, 0L) // questionId 와 userId 가 0인 경우는 없음
        activityUpdateAnswerBinding.answerUpdateButton.setOnClickListener {
            answerViewModel.saveAnswer(answerDTO)
            // POST 요청
            answerViewModel.updateAnswerResponseLiveData.observe(this) { response ->
                response.let {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this@AnswerUpdateActivity,
                            "Your answer has been updated !!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AnswerUpdateActivity,
                            "Internal Error has been occurred. Please try once more",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun setUpViewBinding() {
        activityUpdateAnswerBinding = ActivityUpdateAnswerBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityUpdateAnswerBinding.root)
    }
}