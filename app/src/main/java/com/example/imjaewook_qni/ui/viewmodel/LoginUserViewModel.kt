package com.example.imjaewook_qni.ui.viewmodel

import android.util.Log
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
    lateinit var loginUserLiveData: MutableLiveData<LoginResponseDTO?>
    lateinit var logoutUserLiveData: MutableLiveData<Void?>
    init {
        loginUserLiveData = MutableLiveData()
        logoutUserLiveData = MutableLiveData()
    }

    fun loginUserObserver(): MutableLiveData<LoginResponseDTO?> {
        return loginUserLiveData
    }

    fun logoutUserObserver(): MutableLiveData<Void?> {
        return logoutUserLiveData
    }

    fun login(loginDTO: LoginDTO) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.login(loginDTO)
        call.enqueue(object: Callback<LoginResponseDTO> {
            override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                loginUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<LoginResponseDTO>, response: Response<LoginResponseDTO>) {
                if(response.isSuccessful) {

                    ImJaeWookQniApplication.prefs.setString("userId", response.body()?.userId.toString())
                    ImJaeWookQniApplication.prefs.setString("nickname", response.body()?.nickname.toString())

                    // log
                    Log.v("로그인 시 저장되는 userId : ", ImJaeWookQniApplication.prefs.getString("userId", "0"))
                    Log.v("로그인 시 저장되는 nickname : ", ImJaeWookQniApplication.prefs.getString("nickname", "null"))


                    loginUserLiveData.postValue(response.body())
                } else {
                    loginUserLiveData.postValue(null)
                }
            }
        })
    }

    fun logout() {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.logout()
        call.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                logoutUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {

                    logoutUserLiveData.postValue(response.body())
                } else {
                    logoutUserLiveData.postValue(null)
                }
            }
        })
    }


}