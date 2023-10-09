package com.convertedin.task.data.mapper

import com.convertedin.task.data.model.AlbumDto
import com.convertedin.task.domain.model.Album

fun AlbumDto.toAlbum() = Album(
    id = id,
    userId = userId,
    title = title
)