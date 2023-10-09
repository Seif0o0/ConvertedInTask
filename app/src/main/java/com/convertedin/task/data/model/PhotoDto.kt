package com.convertedin.task.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDto(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)