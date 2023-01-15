package com.example.imjaewook_qni.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.example.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.example.imjaewook_qni.ui.repository.MainRepository
import com.example.imjaewook_qni.ui.repository.UserRepository
import com.example.imjaewook_qni.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _questionAnswerDTOLiveData: MutableLiveData<List<QuestionAnswerDTO>> = MutableLiveData()
    val questionAnswerDTOLiveData: LiveData<List<QuestionAnswerDTO>> = _questionAnswerDTOLiveData

    private val _answeredQuestionDTOLiveData: MutableLiveData<List<AnsweredQuestionDTO>> = MutableLiveData()
    val answeredQuestionDTOLiveData: LiveData<List<AnsweredQuestionDTO>> = _answeredQuestionDTOLiveData

    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast

    private fun setToastMessage(content: String) {
        _showErrorToast.postValue(Event(content))
    }

    fun getUserQuestionList(userId: Long) = viewModelScope.launch(Dispatchers.Main) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            setToastMessage("Error has been occurred bringing user question list")
            throwable.message?.let { Log.d("MainViewModel.kt", it) }
        }

        withContext(Dispatchers.IO + handler) {

            val response = mainRepository.getQuestionList(userId.toString())

            if (response.isSuccessful) {
                setToastMessage("success bringing bringing user question list")
                _questionAnswerDTOLiveData.postValue(response.body())
            } else {
                Log.d("tag", "getUserQuestionList: has an error receiving data")
            }
        }

    }


    fun getAnsweredUserQuestionList(userId: Long) = viewModelScope.launch(Dispatchers.Main) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            setToastMessage("Error has been occurred bringing user answered question list")
            throwable.message?.let { Log.d("MainViewModel.kt", it) }
        }

        withContext(Dispatchers.IO + handler) {

            val response = mainRepository.getAnsweredQuestionList(userId.toString())

            if (response.isSuccessful) {
                setToastMessage("success bringing bringing user answered question list")
                _answeredQuestionDTOLiveData.postValue(response.body())
            } else {
                Log.d("tag", "getUserAnsweredQuestionList: has an error receiving data")
            }
        }

    }


}