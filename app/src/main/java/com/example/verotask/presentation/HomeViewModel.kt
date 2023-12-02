package com.example.verotask.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verotask.data.models.Task
import com.example.verotask.data.repository.BaseRepository
import com.example.verotask.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val baseRepository: BaseRepository
) : ViewModel() {

    private val _getTasksState = MutableLiveData<Resource<List<Task>>>()
    val getTasksState: LiveData<Resource<List<Task>>>
        get() = _getTasksState

    fun getTasks() {
        _getTasksState.value = Resource.Loading()

        viewModelScope.launch {
            baseRepository.getTasks { result ->
                _getTasksState.postValue(result)
            }
        }
    }

}