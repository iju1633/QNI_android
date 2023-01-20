package com.example.imjaewook_qni.ui

import android.content.Intent
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
import com.example.imjaewook_qni.api.dto.NicknameResponseDTO
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

        activitySettingBinding.newName.text =
            ImJaeWookQniApplication.prefs.getString("nickname", "null")

        setUpViewModel()

        activitySettingBinding.nicknameBox.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            val dialogView = layoutInflater.inflate(R.layout.update_nickname_dialog, null)
            val dialogText = dialogView.findViewById<EditText>(R.id.newNickname)

            builder.setView(dialogView)
                .setPositiveButton("save") { _, _ ->
                    ImJaeWookQniApplication.prefs.setString(
                        "answeredNewNickname",
                        dialogText.text.toString()
                    )

                    changeNickname()
                }
                .setNegativeButton("cancel") { _, _ ->

                }
                .setIcon(R.drawable.logo)
                .show()
        }

        activitySettingBinding.withdrawBox.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            val dialogView = layoutInflater.inflate(R.layout.withdraw_user_dialog, null)

            builder.setView(dialogView)
                .setPositiveButton("save") { dialogInterface, i ->

                    withdrawUser()
                }
                .setNegativeButton("cancel") { dialogInterface, i ->

                }
                .setIcon(R.drawable.logo)
                .show()
        }
    }

    private fun changeNickname() {
        val nicknameDTO = NicknameDTO(
            ImJaeWookQniApplication.prefs.getString("answeredNewNickname", "null"),
            ImJaeWookQniApplication.prefs.getString("userId", "0")
        )
        Log.v(
            "전역변수에 저장된 변경 전 유저의 닉네임 : ",
            ImJaeWookQniApplication.prefs.getString("nickname", "null")
        )

        viewModel.changeNickname(nicknameDTO)
    }

    private fun withdrawUser() {
        viewModel.withdrawalUser(ImJaeWookQniApplication.prefs.getString("userId", "0"))

        val intent = Intent(this@SettingActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        viewModel.changeNicknameObserver().observe(this, Observer<NicknameResponseDTO?> {

            if (it != null) {
                Toast.makeText(
                    this@SettingActivity,
                    "Successfully changed nickname",
                    Toast.LENGTH_SHORT
                ).show()

                activitySettingBinding.newName.text =
                    ImJaeWookQniApplication.prefs.getString("answeredNewNickname", "null")
                ImJaeWookQniApplication.prefs.setString(
                    "nickname",
                    ImJaeWookQniApplication.prefs.getString("answeredNewNickname", "null")
                )
            } else {
                Toast.makeText(
                    this@SettingActivity,
                    "Written nickname has been already taken",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        viewModel.withdrawalUserObserver().observe(this, Observer<Void?> {

            if (it == null) { // 반환값이 void 이니까 !
                Toast.makeText(
                    this@SettingActivity,
                    "Successfully deleted user.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@SettingActivity,
                    "Failed to delete user.",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}