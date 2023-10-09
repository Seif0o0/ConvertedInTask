package com.convertedin.task.domain.repository

import com.convertedin.task.domain.model.Album
import com.convertedin.task.domain.model.Photo
import com.convertedin.task.utils.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    suspend fun fetchAlbums(userId: Int): Flow<DataState<List<Album>>>

    suspend fun fetchPhotos(albumId: Int): Flow<DataState<List<Photo>>>
}