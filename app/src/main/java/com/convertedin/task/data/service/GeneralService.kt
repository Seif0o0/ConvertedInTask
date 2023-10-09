package com.convertedin.task.data.service

import com.convertedin.task.data.model.AlbumDto
import com.convertedin.task.data.model.PhotoDto
import com.convertedin.task.data.model.UserDto
import com.convertedin.task.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface GeneralService {

    @GET(Constants.USERS)
    suspend fun fetchUsers():List<UserDto>

    @GET(Constants.ALBUMS)
    suspend fun fetchAlbums(@Query("userId") userId:Int): List<AlbumDto>

    @GET(Constants.PHOTOS)
    suspend fun fetchPhotos(@Query("albumId") albumId: Int): List<PhotoDto>

}