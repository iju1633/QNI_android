package com.example.imjaewook_qni.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.api.dto.AnswerDTO
import com.example.imjaewook_qni.databinding.ActivityMainBinding
import com.example.imjaewook_qni.databinding.ActivitySaveAnswerBinding
import com.example.imjaewook_qni.databinding.ActivityUpdateAnswerBinding
import com.example.imjaewook_qni.ui.viewmodel.AnswerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerSaveActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var activitySaveAnswerBinding: ActivitySaveAnswerBinding

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

        answerViewModel.questionAnswerDTOLiveData.observe(this) { question ->

            val secondIntent = intent
            val chosenQuestionId = secondIntent.getIntExtra("ChosenQuestionId", 0)

            activitySaveAnswerBinding.idAndQuestion.text = question[chosenQuestionId].questionId.toString() + ". " + question[chosenQuestionId].question
            activitySaveAnswerBinding.answerBox.setText("A. " + question[chosenQuestionId].answer)
        }

        answerViewModel.saveAnswerResponseLiveData.observe(this) { response ->
            response.let {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this@AnswerSaveActivity,
                        "Your answer has been saved !!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this@AnswerSaveActivity,
                        "You already answered this question.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setUpUI() {
        setUpViewBinding()

        activitySaveAnswerBinding.answerSaveButton.setOnClickListener {

            val answer = activitySaveAnswerBinding.answerBox.text.toString()
            val questionId = activitySaveAnswerBinding.idAndQuestion.text.subSequence(0, 1).toString().toLong()
            val userId = 3L

            val answerDTO = AnswerDTO(answer, questionId, userId)

            answerViewModel.saveAnswer(answerDTO)
        }
    }

    private fun setUpViewBinding() {
        activitySaveAnswerBinding = ActivitySaveAnswerBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activitySaveAnswerBinding.root)
    }
}