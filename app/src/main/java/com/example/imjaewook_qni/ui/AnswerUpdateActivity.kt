package com.example.imjaewook_qni.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.R
import com.example.imjaewook_qni.api.dto.AnswerDTO
import com.example.imjaewook_qni.api.dto.AnswerUpdateDTO
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
            val chosenQuestionId = secondIntent.getIntExtra("ChosenAnsweredQuestionId", 0) // TODO: 값이 안 넘어왔음

            val builder = AlertDialog.Builder(this)

            val dialogView = layoutInflater.inflate(R.layout.update_answer_dialog, null)
            val dialogText = dialogView.findViewById<EditText>(R.id.newAnswer)

            builder.setView(dialogView)
                .setPositiveButton("save") { dialogInterface, i ->
                    activityUpdateAnswerBinding.answerBox.text = dialogText.text.toString()
                }
                .setNegativeButton("cancel") {dialogInterface, i ->

                }
                .setIcon(R.drawable.logo)
                .show()

            activityUpdateAnswerBinding.answerUpdateButton.setOnClickListener {
                val answerUpdateDTO = AnswerUpdateDTO(dialogText.text.toString(), "3", 3L)

                answerViewModel.updateAnswer(answerUpdateDTO)
            }
        }
    }

    private fun setUpViewBinding() {
        activityUpdateAnswerBinding = ActivityUpdateAnswerBinding.inflate(layoutInflater)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityUpdateAnswerBinding.root)
    }
}