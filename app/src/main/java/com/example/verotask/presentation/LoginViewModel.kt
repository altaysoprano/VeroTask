package com.example.verotask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verotask.data.repository.BaseRepository
import com.example.verotask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<String>>()
    val loginState: LiveData<Resource<String>>
        get() = _loginState

    fun login(username: String, password: String) {
        _loginState.value = Resource.Loading()

        viewModelScope.launch {
            baseRepository.getAccessToken(username, password) { result ->
                _loginState.postValue(result)
            }
        }
    }
}
