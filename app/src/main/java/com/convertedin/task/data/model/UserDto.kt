package com.convertedin.task.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: Int,
    val name: String,
    val address: AddressDto
)