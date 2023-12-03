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

    private val _swipeState = MutableLiveData<Resource<List<Task>>>()
    val swipeState: LiveData<Resource<List<Task>>>
        get() = _swipeState

    private var originalTasks: List<Task> = emptyList()
    private val _filteredTasks = MutableLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> get() = _filteredTasks

    fun setOriginalTasks(tasks: List<Task>) {
        originalTasks = tasks
        _filteredTasks.value = tasks
    }

    fun filterTasks(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalTasks
        } else {
            originalTasks.filter { task ->
                task.title.contains(query, ignoreCase = true) ||
                        task.description.contains(query, ignoreCase = true) ||
                        task.task.contains(query, ignoreCase = true)
            }
        }
        _filteredTasks.value = filteredList
    }

    fun getTasks() {
        _getTasksState.value = Resource.Loading()

        viewModelScope.launch {
            baseRepository.getTasks { result ->
                _getTasksState.postValue(result)
            }
        }
    }

    fun onSwipe() {
        _swipeState.value = Resource.Loading()

        viewModelScope.launch {
            baseRepository.getTasks { result ->
                _swipeState.postValue(result)
            }
        }
    }

}