package com.convertedin.task.data.repository

import android.util.Log
import com.convertedin.task.R
import com.convertedin.task.data.mapper.toUser
import com.convertedin.task.data.service.GeneralService
import com.convertedin.task.domain.model.User
import com.convertedin.task.domain.repository.HomeRepo
import com.convertedin.task.utils.DataState
import com.convertedin.task.utils.StringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val service: GeneralService
) : HomeRepo {
    override suspend fun fetchUsers(): Flow<DataState<List<User>>> {
        return flow {
            try {
                emit(DataState.Loading())
                val response = service.fetchUsers()
                emit(DataState.Success(data = response.map { it.toUser() }))
            } catch (e: IOException) {
                emit(DataState.Error(message = StringResource.getString(R.string.no_internet_connection)))
            } catch (e: Exception) {
                emit(DataState.Error(message = StringResource.getString(R.string.something_went_wrong_error_message)))
            }
        }
    }
}