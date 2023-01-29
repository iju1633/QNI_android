package com.qni.imjaewook_qni.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.qni.imjaewook_qni.ImJaeWookQniApplication
import com.qni.imjaewook_qni.api.dto.AnswerDTO
import com.qni.imjaewook_qni.databinding.ActivityMainBinding
import com.qni.imjaewook_qni.databinding.ActivitySaveAnswerBinding
import com.qni.imjaewook_qni.databinding.ActivityUpdateAnswerBinding
import com.qni.imjaewook_qni.ui.viewmodel.AnswerViewModel
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

        answerViewModel.getUserQuestionList(ImJaeWookQniApplication.prefs.getString("userId", "0").toLong())
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViewModel() {

        answerViewModel.questionAnswerDTOLiveData.observe(this) { question ->

            val secondIntent = intent
            val chosenQuestionId = secondIntent.getIntExtra("ChosenQuestionId", 0)

            Log.v("답변할 질문의 id : ", chosenQuestionId.toString())

            activitySaveAnswerBinding.idAndQuestion.text = question[chosenQuestionId].questionId.toString() + ". " + question[chosenQuestionId].question
            activitySaveAnswerBinding.answerBox.setText(question[chosenQuestionId].answer)
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
            val questionId = activitySaveAnswerBinding.idAndQuestion.text.subSequence(0, activitySaveAnswerBinding.idAndQuestion.text.indexOf(".")).toString().toLong()
            val userId = ImJaeWookQniApplication.prefs.getString("userId", "0").toLong()

            val answerDTO = AnswerDTO(answer, questionId, userId)

            answerViewModel.saveAnswer(answerDTO)
        }

        activitySaveAnswerBinding.backToMain.setOnClickListener {

            finish()

            val intent = Intent(this@AnswerSaveActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpViewBinding() {
        activitySaveAnswerBinding = ActivitySaveAnswerBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activitySaveAnswerBinding.root)
    }
}