package com.convertedin.task.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.convertedin.task.domain.model.User
import com.convertedin.task.domain.repository.HomeRepo
import com.convertedin.task.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepo
) : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> get() = _errorState

    private val _emptyState = MutableStateFlow(View.GONE)
    val emptyState: StateFlow<Int> get() = _emptyState

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            repo.fetchUsers().collectLatest { result ->
                when (result) {
                    is DataState.Loading -> {
                        _loadingState.emit(true)
                        _errorState.emit("")
                        _emptyState.emit(View.GONE)
                    }

                    is DataState.Success -> {
                        _loadingState.emit(false)

                        val usersList = result.data!!
                        if (usersList.isEmpty()) {
                            _emptyState.emit(View.VISIBLE)
                            _users.emit(emptyList())
                        } else {
                            _emptyState.emit(View.GONE)
                            _users.emit(usersList)
                        }
                    }

                    is DataState.Error -> {
                        _loadingState.emit(false)
                        _errorState.emit(result.message!!)
                    }
                }
            }
        }
    }
}