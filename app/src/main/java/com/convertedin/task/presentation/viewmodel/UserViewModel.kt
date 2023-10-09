package com.convertedin.task.presentation.viewmodel

import android.view.View
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.convertedin.task.domain.model.Album
import com.convertedin.task.domain.model.User
import com.convertedin.task.domain.repository.UserRepo
import com.convertedin.task.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepo, private val savedState: SavedStateHandle
) : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> get() = _errorState

    private val _emptyState = MutableStateFlow(View.GONE)
    val emptyState: StateFlow<Int> get() = _emptyState

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> get() = _albums

    init {
        getAlbums()
    }

    fun getAlbums() {
        viewModelScope.launch {
            val user = savedState.get<User>("user")!!
            repo.fetchAlbums(userId = user.id).collectLatest { result ->
                when (result) {
                    is DataState.Loading -> {
                        _loadingState.emit(true)
                        _errorState.emit("")
                        _emptyState.emit(View.GONE)
                    }

                    is DataState.Success -> {
                        _loadingState.emit(false)

                        val albumsList = result.data!!
                        if (albumsList.isEmpty()) {
                            _emptyState.emit(View.VISIBLE)
                            _albums.emit(emptyList())
                        } else {
                            _emptyState.emit(View.GONE)
                            _albums.emit(albumsList)
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