package com.convertedin.task.data.repository

import com.convertedin.task.R
import com.convertedin.task.data.mapper.toAlbum
import com.convertedin.task.data.mapper.toPhoto
import com.convertedin.task.data.mapper.toUser
import com.convertedin.task.data.service.GeneralService
import com.convertedin.task.domain.model.Album
import com.convertedin.task.domain.model.Photo
import com.convertedin.task.domain.repository.UserRepo
import com.convertedin.task.utils.DataState
import com.convertedin.task.utils.StringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UserRepoImpl @Inject constructor(
    private val service: GeneralService
) : UserRepo {
    override suspend fun fetchAlbums(userId: Int): Flow<DataState<List<Album>>> {
        return flow {
            try {
                emit(DataState.Loading())
                val response = service.fetchAlbums(userId = userId)
                emit(DataState.Success(data = response.map { it.toAlbum() }))
            } catch (e: IOException) {
                emit(DataState.Error(message = StringResource.getString(R.string.no_internet_connection)))
            } catch (e: Exception) {
                emit(DataState.Error(message = StringResource.getString(R.string.something_went_wrong_error_message)))
            }
        }
    }

    override suspend fun fetchPhotos(albumId: Int): Flow<DataState<List<Photo>>> {
        return flow {
            try {
                emit(DataState.Loading())
                val response = service.fetchPhotos(albumId = albumId)
                emit(DataState.Success(data = response.map { it.toPhoto() }))
            } catch (e: IOException) {
                emit(DataState.Error(message = StringResource.getString(R.string.no_internet_connection)))
            } catch (e: Exception) {
                emit(DataState.Error(message = StringResource.getString(R.string.something_went_wrong_error_message)))
            }
        }
    }
}