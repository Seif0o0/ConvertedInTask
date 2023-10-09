package com.convertedin.task.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumDto(
    val id: Int,
    val userId: Int,
    val title: String
)