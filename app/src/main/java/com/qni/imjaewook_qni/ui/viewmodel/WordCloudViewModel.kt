package com.qni.imjaewook_qni.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qni.imjaewook_qni.api.RetroInstance
import com.qni.imjaewook_qni.api.RetroServiceInterface
import com.qni.imjaewook_qni.api.dto.CombinedAnswerDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordCloudViewModel : ViewModel() {
    var worldCloudLiveData: MutableLiveData<CombinedAnswerDTO?> = MutableLiveData()

    fun wordCloudObserver(): MutableLiveData<CombinedAnswerDTO?> {
        return worldCloudLiveData
    }

    fun getCombinedAnswer(userId: String) {
        val retroService =
            RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getCombinedAnswer(userId)
        call.enqueue(object : Callback<CombinedAnswerDTO> {
            override fun onFailure(call: Call<CombinedAnswerDTO>, t: Throwable) {
                worldCloudLiveData.postValue(null)
            }

            override fun onResponse(
                call: Call<CombinedAnswerDTO>,
                response: Response<CombinedAnswerDTO>
            ) {
                if (response.isSuccessful) {
                    worldCloudLiveData.postValue(response.body())
                } else {
                    worldCloudLiveData.postValue(null)
                }
            }
        })
    }
}