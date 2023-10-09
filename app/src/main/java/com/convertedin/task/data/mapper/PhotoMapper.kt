package com.convertedin.task.data.mapper

import com.convertedin.task.data.model.PhotoDto
import com.convertedin.task.domain.model.Photo

fun PhotoDto.toPhoto() = Photo(
    id = id, albumId = albumId, title = title, url = url, thumbnailUrl = thumbnailUrl
)