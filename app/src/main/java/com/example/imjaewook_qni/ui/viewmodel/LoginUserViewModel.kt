package com.example.imjaewook_qni.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.RetroInstance
import com.example.imjaewook_qni.api.RetroServiceInterface
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginUserViewModel: ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<LoginResponseDTO?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<LoginResponseDTO?> {
        return createNewUserLiveData
    }

    fun login(loginDTO: LoginDTO) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.login(loginDTO)
        call.enqueue(object: Callback<LoginResponseDTO> {
            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<LoginResponseDTO>, response: Response<LoginResponseDTO>) {
                if(response.isSuccessful) {

                    ImJaeWookQniApplication.userId = response.body()?.userId.toString()
                    ImJaeWookQniApplication.nickname = response.body()?.nickname.toString()

                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }


}