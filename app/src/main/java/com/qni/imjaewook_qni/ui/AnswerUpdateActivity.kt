package com.qni.imjaewook_qni.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.qni.imjaewook_qni.ImJaeWookQniApplication
import com.qni.imjaewook_qni.R
import com.qni.imjaewook_qni.api.dto.AnswerUpdateDTO
import com.qni.imjaewook_qni.databinding.ActivityMainBinding
import com.qni.imjaewook_qni.databinding.ActivityUpdateAnswerBinding
import com.qni.imjaewook_qni.ui.viewmodel.AnswerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnswerUpdateActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var activityUpdateAnswerBinding: ActivityUpdateAnswerBinding

    private val answerViewModel: AnswerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUI()
        setUpViewModel()
    }

    override fun onStart() {
        super.onStart()

        answerViewModel.getAnsweredUserQuestionList(
            ImJaeWookQniApplication.prefs.getString(
                "userId",
                "0"
            ).toLong()
        )
    }

    @SuppressLint("SetTextI18n")
    private fun setUpViewModel() {

        answerViewModel.answeredQuestionDTOLiveData.observe(this) { question ->

            val secondIntent = intent
            val chosenAnsweredQuestionId = secondIntent.getIntExtra("ChosenAnsweredQuestionId", 0)

            Log.v("답변된 질문의 id : ", chosenAnsweredQuestionId.toString())

            activityUpdateAnswerBinding.question.text =
                question[chosenAnsweredQuestionId].questionId.toString() + ". " + question[chosenAnsweredQuestionId].question
            activityUpdateAnswerBinding.answerBox.text = question[chosenAnsweredQuestionId].answer
        }

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
                        "You should write at least one word.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setUpUI() {
        setUpViewBinding()

        activityUpdateAnswerBinding.answerBox.setOnClickListener {

            val secondIntent = intent
            val chosenAnsweredQuestionId = secondIntent.getIntExtra("ChosenAnsweredQuestionId", 0)

            Log.v("답변을 수정할 질문의 id : ", chosenAnsweredQuestionId.toString())

            val builder = AlertDialog.Builder(this)

            val dialogView = layoutInflater.inflate(R.layout.update_answer_dialog, null)
            val dialogText = dialogView.findViewById<EditText>(R.id.newAnswer)

            builder.setView(dialogView)
                .setPositiveButton("save") { _, _ ->
                    activityUpdateAnswerBinding.answerBox.text = dialogText.text.toString()

                }
                .setNegativeButton("cancel") { _, _ ->

                }
                .setIcon(R.drawable.logo)
                .show()

            activityUpdateAnswerBinding.answerUpdateButton.setOnClickListener {

                val answerUpdateDTO = AnswerUpdateDTO(
                    dialogText.text.toString(),
                    activityUpdateAnswerBinding.question.text.substring(
                        0,
                        activityUpdateAnswerBinding.question.text.indexOf(".")
                    ),
                    ImJaeWookQniApplication.prefs.getString("userId", "0").toLong()
                )
                answerViewModel.updateAnswer(answerUpdateDTO)
            }
        }

        activityUpdateAnswerBinding.backToMain.setOnClickListener {

            finish()

            val intent = Intent(this@AnswerUpdateActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpViewBinding() {
        activityUpdateAnswerBinding = ActivityUpdateAnswerBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityUpdateAnswerBinding.root)
    }
}