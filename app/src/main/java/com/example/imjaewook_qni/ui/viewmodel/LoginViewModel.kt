package com.example.imjaewook_qni.ui.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imjaewook_qni.ImJaeWookQniApplication
import com.example.imjaewook_qni.api.dto.LoginDTO
import com.example.imjaewook_qni.api.dto.LoginResponseDTO
import com.example.imjaewook_qni.ui.repository.UserRepository
import com.example.imjaewook_qni.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginResponseDTOLiveData: MutableLiveData<Response<LoginResponseDTO>> = MutableLiveData()
    val loginResponseDTOLiveData: LiveData<Response<LoginResponseDTO>> = _loginResponseDTOLiveData

    private val _showErrorToast = MutableLiveData<Event<String>>()
    val showErrorToast: LiveData<Event<String>> = _showErrorToast

    private fun setToastMessage(content: String) {
        _showErrorToast.postValue(Event(content))
    }

    fun userLogin(loginDTO: LoginDTO) =
        viewModelScope.launch(Dispatchers.Main) {

            val handler = CoroutineExceptionHandler { _, throwable ->
                setToastMessage("Error has been occurred while login.")
                throwable.message?.let { Log.d("LoginViewModel.kt", it) }
            }

            withContext(Dispatchers.IO + handler) {
                val response = userRepository.userLogin(loginDTO)
                _loginResponseDTOLiveData.postValue(response)
            }
        }
}