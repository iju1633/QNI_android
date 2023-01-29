package com.qni.imjaewook_qni.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qni.imjaewook_qni.ImJaeWookQniApplication
import com.qni.imjaewook_qni.api.RetroInstance
import com.qni.imjaewook_qni.api.RetroServiceInterface
import com.qni.imjaewook_qni.api.dto.NicknameDTO
import com.qni.imjaewook_qni.api.dto.NicknameResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingViewModel : ViewModel() {
    var changeNicknameLiveData: MutableLiveData<NicknameResponseDTO> = MutableLiveData()
    var withdrawUserLiveData: MutableLiveData<Void> = MutableLiveData()

    fun changeNicknameObserver(): MutableLiveData<NicknameResponseDTO> {
        return changeNicknameLiveData
    }

    fun withdrawalUserObserver(): MutableLiveData<Void> {
        return withdrawUserLiveData
    }

    fun changeNickname(nicknameDTO: NicknameDTO) {
        val retroService =
            RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.changeNickname(nicknameDTO)
        call.enqueue(object : Callback<NicknameResponseDTO> {
            override fun onFailure(call: Call<NicknameResponseDTO>, t: Throwable) {
                changeNicknameLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<NicknameResponseDTO>,
                response: Response<NicknameResponseDTO>
            ) {
                if (response.isSuccessful) {
                    ImJaeWookQniApplication.prefs.setString(
                        "nickname",
                        response.body()?.newNickname.toString()
                    )
                    changeNicknameLiveData.postValue(response.body())
                } else {
                    changeNicknameLiveData.postValue(null)
                }
            }
        })
    }

    fun withdrawalUser(userId: String) {
        val retroService =
            RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.withdrawalUser(userId)
        call.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                withdrawUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {

                    ImJaeWookQniApplication.prefs.setString("userId", "0")
                    ImJaeWookQniApplication.prefs.setString("nickname", "null")

                    withdrawUserLiveData.postValue(response.body())
                } else {
                    withdrawUserLiveData.postValue(null)
                }
            }
        })
    }


}