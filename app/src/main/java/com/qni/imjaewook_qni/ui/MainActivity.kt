package com.qni.imjaewook_qni.ui

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qni.imjaewook_qni.ImJaeWookQniApplication
import com.qni.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.qni.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.qni.imjaewook_qni.databinding.ActivityMainBinding
import com.qni.imjaewook_qni.databinding.ActivitySaveAnswerBinding
import com.qni.imjaewook_qni.databinding.ActivityUpdateAnswerBinding
import com.qni.imjaewook_qni.ui.adapter.MainAnsweredQuestionAdapter
import com.qni.imjaewook_qni.ui.adapter.MainQuestionAdapter
import com.qni.imjaewook_qni.ui.viewmodel.LoginViewModel
import com.qni.imjaewook_qni.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var mainQuestionAdapter: MainQuestionAdapter
    lateinit var mainAnsweredQuestionAdapter: MainAnsweredQuestionAdapter

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var activitySaveAnswerBinding: ActivitySaveAnswerBinding
    private lateinit var activityUpdateAnswerBinding: ActivityUpdateAnswerBinding

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var viewModel: LoginViewModel

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
            val intent = Intent(this@MainActivity, WordCloudLoadingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        // test
        Log.v(
            "답변된 질문, 모든 질문 가져올 때 사용할 userId : ",
            ImJaeWookQniApplication.prefs.getString("userId", "0")
        )

        mainViewModel.getAnsweredUserQuestionList(
            ImJaeWookQniApplication.prefs.getString(
                "userId",
                "0"
            ).toLong()
        )
        mainViewModel.getUserQuestionList(
            ImJaeWookQniApplication.prefs.getString("userId", "0").toLong()
        )

    }

    override fun onResume() {
        super.onResume()

        activityMainBinding.nickname.text = ImJaeWookQniApplication.prefs.getString("nickname", "null")
    }

    private fun setUpViewModel() {

        mainViewModel.questionAnswerDTOLiveData.observe(this) { questionResponse ->

            activityMainBinding.apply {
                mainQuestionAdapter.itemList = questionResponse
            }
        }

        mainViewModel.answeredQuestionDTOLiveData.observe(this) { answeredQuestionResponse ->

            activityMainBinding.apply {
                mainAnsweredQuestionAdapter.itemList = answeredQuestionResponse
            }
        }

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.logoutUserObserver().observe(this) {

            if (it == null) {
                Toast.makeText(this@MainActivity, "Successfully logout !!", Toast.LENGTH_SHORT)
                    .show()
            } else {

                Toast.makeText(this@MainActivity, "Failed to logout !!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setUpUI() {
        setUpViewBinding()
        initRecyclerView()

        activityMainBinding.nickname.text =
            ImJaeWookQniApplication.prefs.getString("nickname", "null")
        mainViewModel.getAnsweredUserQuestionList(
            ImJaeWookQniApplication.prefs.getString(
                "userId",
                "0"
            ).toLong()
        )
        mainViewModel.getUserQuestionList(
            ImJaeWookQniApplication.prefs.getString("userId", "0").toLong()
        )

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

        activityMainBinding.logout.setOnClickListener {

            logout()
        }
    }

    private fun logout() {

        ImJaeWookQniApplication.prefs.setString("userId", "0")
        ImJaeWookQniApplication.prefs.setString("nickname", "null")

        viewModel.logout()

        finish()

        val nextIntent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(nextIntent)
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