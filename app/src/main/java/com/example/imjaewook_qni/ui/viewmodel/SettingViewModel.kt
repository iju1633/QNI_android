package com.example.imjaewook_qni.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.RetroInstance
import com.example.imjaewook_qni.api.RetroServiceInterface
import com.example.imjaewook_qni.api.dto.NicknameDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingViewModel: ViewModel() {
    lateinit var changeNicknameLiveData: MutableLiveData<Void>
    init {
        changeNicknameLiveData = MutableLiveData()
    }

    fun changeNicknameObserver(): MutableLiveData<Void> {
        return changeNicknameLiveData
    }

    fun changeNickname(nicknameDTO: NicknameDTO) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.changeNickname(nicknameDTO)
        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                changeNicknameLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {

                    changeNicknameLiveData.postValue(response.body())
                } else {
                    changeNicknameLiveData.postValue(null)
                }
            }
        })
    }
}