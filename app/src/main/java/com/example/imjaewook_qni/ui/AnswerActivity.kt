package com.example.imjaewook_qni.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imjaewook_qni.databinding.ActivityAnswerBinding
import kotlinx.android.synthetic.main.activity_answer.*

class AnswerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // save 버튼 누를 시,
        // TODO : question ID 필요한 지 판단할 것
        answerSaveButton.setOnClickListener {

            val answer = answerBox.text.toString().trim()

        }
    }
}