package com.example.imjaewook_qni.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imjaewook_qni.api.dto.RegisterDTO
import com.example.imjaewook_qni.ui.repository.UserRepository
import com.example.imjaewook_qni.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _registerResponseLiveData: MutableLiveData<Response<Void>> = MutableLiveData()
    val registerResponseLiveData: LiveData<Response<Void>> = _registerResponseLiveData

    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast

    private fun setToastMessage(content: String) {
        _showErrorToast.postValue(Event(content))
    }

    fun userRegister(registerDTO: RegisterDTO) =
        viewModelScope.launch(Dispatchers.Main) {

            val handler = CoroutineExceptionHandler { _, throwable ->
                setToastMessage("Error has been occurred while register.")
                throwable.message?.let { Log.d("LoginViewModel.kt", it) }
            }

            withContext(Dispatchers.IO + handler) {
                val response =
                    userRepository.userRegister(registerDTO)
                _registerResponseLiveData.postValue(response)
            }
        }
}