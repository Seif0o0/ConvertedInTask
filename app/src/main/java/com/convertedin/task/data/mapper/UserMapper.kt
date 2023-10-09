package com.convertedin.task.data.mapper

import com.convertedin.task.data.model.UserDto
import com.convertedin.task.domain.model.User

fun UserDto.toUser() = User(
    id = id,
    name = name,
    address = address.toAddress()
)