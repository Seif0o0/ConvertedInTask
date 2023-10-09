package com.convertedin.task.domain.repository

import com.convertedin.task.domain.model.User
import com.convertedin.task.utils.DataState
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    suspend fun fetchUsers(): Flow<DataState<List<User>>>
}