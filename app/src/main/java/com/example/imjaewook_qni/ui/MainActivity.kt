package com.example.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.example.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.example.imjaewook_qni.databinding.ActivityMainBinding
import com.example.imjaewook_qni.databinding.ActivitySaveAnswerBinding
import com.example.imjaewook_qni.databinding.ActivityUpdateAnswerBinding
import com.example.imjaewook_qni.ui.adapter.MainAnsweredQuestionAdapter
import com.example.imjaewook_qni.ui.adapter.MainQuestionAdapter
import com.example.imjaewook_qni.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainQuestionAdapter: MainQuestionAdapter
    lateinit var mainAnsweredQuestionAdapter: MainAnsweredQuestionAdapter

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var activitySaveAnswerBinding: ActivitySaveAnswerBinding
    private lateinit var activityUpdateAnswerBinding: ActivityUpdateAnswerBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUI()
        setUpViewModel()

        activityMainBinding.logout.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        activityMainBinding.settingButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            startActivity(intent)
        }

        activityMainBinding.wordCloudButton.setOnClickListener {
            val intent = Intent(this@MainActivity, WordCloudActivity::class.java)
            startActivity(intent)
        }

        activityMainBinding.logout.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        mainViewModel.getAnsweredUserQuestionList(ImJaeWookQniApplication.userId.toLong())
        mainViewModel.getUserQuestionList(ImJaeWookQniApplication.userId.toLong())
    }

    private fun setUpViewModel() {
        mainViewModel.questionAnswerDTOLiveData.observe(this) { questionResponse ->

            activityMainBinding.apply {
                mainQuestionAdapter.itemList = questionResponse
            }

            // 답변이 완료된 문제의 경우, 해당 문제의 id를 전역변수로 선언한 배열에 저장
            for(questionAnswerDTO : QuestionAnswerDTO in questionResponse) {
                if(questionAnswerDTO.answer.isNotEmpty()) {
                    ImJaeWookQniApplication.answeredQuestionIdList.distinct().plus(questionAnswerDTO.questionId)

                    Log.v("(선택될 때 아님) 답안이 작성된 질문의 id : " , questionAnswerDTO.questionId.toString())
                }
            }
        }

        mainViewModel.answeredQuestionDTOLiveData.observe(this) { answeredQuestionResponse ->

            activityMainBinding.apply {
                mainAnsweredQuestionAdapter.itemList = answeredQuestionResponse
            }
        }
    }

    private fun setUpUI() {
        setUpViewBinding()
        initRecyclerView()

        activityMainBinding.nickname.text = ImJaeWookQniApplication.nickname

        mainQuestionAdapter.setOnItemClickListener(object :
            MainQuestionAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val nextIntent = Intent(this@MainActivity, AnswerSaveActivity::class.java)

                nextIntent.putExtra("ChosenQuestionId", position)
                startActivity(nextIntent)
            }
        })

        mainAnsweredQuestionAdapter.setOnItemClickListener(object :
            MainAnsweredQuestionAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val nextIntent = Intent(this@MainActivity, AnswerUpdateActivity::class.java)

                nextIntent.putExtra("ChosenAnsweredQuestionId", position)
                startActivity(nextIntent)
            }
        })
    }

    private fun initRecyclerView() {
        // init adapter
        val questionAnswerDTO = QuestionAnswerDTO("", "", 0L)
        val answeredQuestionDTO = AnsweredQuestionDTO("", "", 0L)

        mainQuestionAdapter = MainQuestionAdapter(listOf(questionAnswerDTO))
        mainAnsweredQuestionAdapter = MainAnsweredQuestionAdapter(listOf(answeredQuestionDTO))

        activityMainBinding.questionRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        activityMainBinding.answeredQuestionRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        activityMainBinding.questionRecyclerView.adapter = mainQuestionAdapter
        activityMainBinding.answeredQuestionRecyclerView.adapter = mainAnsweredQuestionAdapter
    }

    private fun setUpViewBinding() {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        activitySaveAnswerBinding = ActivitySaveAnswerBinding.inflate(layoutInflater)
        activityUpdateAnswerBinding = ActivityUpdateAnswerBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }
}