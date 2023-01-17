package com.example.imjaewook_qni.ui

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.R
import com.example.imjaewook_qni.api.dto.NicknameDTO
import com.example.imjaewook_qni.databinding.ActivityLoginBinding
import com.example.imjaewook_qni.databinding.ActivitySettingBinding
import com.example.imjaewook_qni.ui.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    private lateinit var activitySettingBinding: ActivitySettingBinding
    lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(activitySettingBinding.root)

        activitySettingBinding.newName.text = ImJaeWookQniApplication.nickname

        setUpViewModel()

        activitySettingBinding.nicknameBox.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            val dialogView = layoutInflater.inflate(R.layout.update_nickname_dialog, null)
            val dialogText = dialogView.findViewById<EditText>(R.id.newNickname)

            builder.setView(dialogView)
                .setPositiveButton("save") { dialogInterface, i ->
                    activitySettingBinding.newName.text = dialogText.text.toString()

                    changeNickname()
                }
                .setNegativeButton("cancel") { dialogInterface, i ->

                }
                .setIcon(R.drawable.logo)
                .show()
        }
    }

    private fun changeNickname() {
        val nicknameDTO = NicknameDTO(
            activitySettingBinding.newName.text.toString(),
            ImJaeWookQniApplication.userId
        )
        Log.v("전역변수에 저장된 변경 전 유저의 닉네임 : ", ImJaeWookQniApplication.nickname)

        viewModel.changeNickname(nicknameDTO)

        ImJaeWookQniApplication.nickname = activitySettingBinding.newName.text.toString()
        Log.v("전역변수에 저장된 바뀐 새로운 유저의 닉네임 : ", ImJaeWookQniApplication.nickname)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        viewModel.changeNicknameObserver().observe(this, Observer<Void?> {

            if (it == null) { // 반환값이 void 이니까 !
                Toast.makeText(
                    this@SettingActivity,
                    "Successfully changed nickname.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@SettingActivity,
                    "Failed to change nickname.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}