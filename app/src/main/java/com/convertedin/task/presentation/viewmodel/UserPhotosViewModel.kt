package com.convertedin.task.presentation.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.convertedin.task.domain.model.Album
import com.convertedin.task.domain.model.Photo
import com.convertedin.task.domain.repository.UserRepo
import com.convertedin.task.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPhotosViewModel @Inject constructor(
    private val repo: UserRepo,
    private val savedState: SavedStateHandle
) : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> get() = _errorState

    private val _emptyState = MutableStateFlow(View.GONE)
    val emptyState: StateFlow<Int> get() = _emptyState

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> get() = _photos

    val search = MutableLiveData("")

    fun updateEmptyVisibilityValue(visibility: Int) {
        _emptyState.value = visibility
    }

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            repo.fetchPhotos(albumId = savedState.get<Int>("album_id")!!).collectLatest { result ->
                when (result) {
                    is DataState.Loading -> {
                        _loadingState.emit(true)
                        _errorState.emit("")
                        _emptyState.emit(View.GONE)
                    }

                    is DataState.Success -> {
                        _loadingState.emit(false)

                        val photosList = result.data!!
                        if (photosList.isEmpty()) {
                            _emptyState.emit(View.VISIBLE)
                            _photos.emit(emptyList())
                        } else {
                            _emptyState.emit(View.GONE)
                            _photos.emit(photosList)
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