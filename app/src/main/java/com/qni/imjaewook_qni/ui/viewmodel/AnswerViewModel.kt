package com.qni.imjaewook_qni.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qni.imjaewook_qni.api.dto.AnsweredQuestionDTO
import com.qni.imjaewook_qni.api.dto.QuestionAnswerDTO
import com.qni.imjaewook_qni.api.dto.AnswerDTO
import com.qni.imjaewook_qni.api.dto.AnswerUpdateDTO
import com.qni.imjaewook_qni.ui.repository.MainRepository
import com.qni.imjaewook_qni.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _saveAnswerResponseLiveData: MutableLiveData<Response<Void>> = MutableLiveData()
    val saveAnswerResponseLiveData: LiveData<Response<Void>> = _saveAnswerResponseLiveData

    private val _updateAnswerResponseLiveData: MutableLiveData<Response<Void>> = MutableLiveData()
    val updateAnswerResponseLiveData: LiveData<Response<Void>> = _updateAnswerResponseLiveData

    private val _questionAnswerDTOLiveData: MutableLiveData<List<QuestionAnswerDTO>> =
        MutableLiveData()
    val questionAnswerDTOLiveData: LiveData<List<QuestionAnswerDTO>> = _questionAnswerDTOLiveData

    private val _answeredQuestionDTOLiveData: MutableLiveData<List<AnsweredQuestionDTO>> =
        MutableLiveData()
    val answeredQuestionDTOLiveData: LiveData<List<AnsweredQuestionDTO>> =
        _answeredQuestionDTOLiveData

    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast

    private fun setToastMessage(content: String) {
        _showErrorToast.postValue(Event(content))
    }

    fun getUserQuestionList(userId: Long) = viewModelScope.launch(Dispatchers.Main) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            setToastMessage("Error has been occurred bringing user question list")
            throwable.message?.let { Log.d("AnswerViewModel.kt", it) }
        }

        withContext(Dispatchers.IO + handler) {

            val response = mainRepository.getQuestionList(userId.toString())

            if (response.isSuccessful) {
                setToastMessage("success bringing user question list")
                _questionAnswerDTOLiveData.postValue(response.body())
            } else {
                Log.d("tag", "getUserQuestionList: has an error receiving data")
            }
        }

    }

    fun getAnsweredUserQuestionList(userId: Long) = viewModelScope.launch(Dispatchers.Main) {

        val handler = CoroutineExceptionHandler { _, throwable ->
            setToastMessage("Error has been occurred bringing user answered question list")
            throwable.message?.let { Log.d("AnswerViewModel.kt", it) }
        }

        withContext(Dispatchers.IO + handler) {

            val response = mainRepository.getAnsweredQuestionList(userId.toString())

            if (response.isSuccessful) {
                setToastMessage("success bringing user answered question list")
                _answeredQuestionDTOLiveData.postValue(response.body())
            } else {
                Log.d("tag", "getUserAnsweredQuestionList: has an error receiving data")
            }
        }

    }

    fun saveAnswer(answerDTO: AnswerDTO) =
        viewModelScope.launch(Dispatchers.Main) {

            val handler = CoroutineExceptionHandler { _, throwable ->
                setToastMessage("Error has been occurred saving answer.")
                throwable.message?.let { Log.d("AnswerViewModel.kt", it) }
            }

            withContext(Dispatchers.IO + handler) {
                val response =
                    mainRepository.saveAnswer(answerDTO)
                _saveAnswerResponseLiveData.postValue(response)
            }
        }

    fun updateAnswer(answerUpdateDTO: AnswerUpdateDTO) =
        viewModelScope.launch(Dispatchers.Main) {

            val handler = CoroutineExceptionHandler { _, throwable ->
                setToastMessage("Error has been occurred updating answer.")
                throwable.message?.let { Log.d("AnswerViewModel.kt", it) }
            }

            withContext(Dispatchers.IO + handler) {
                val response =
                    mainRepository.updateAnswer(answerUpdateDTO)
                _updateAnswerResponseLiveData.postValue(response)
            }
        }
}