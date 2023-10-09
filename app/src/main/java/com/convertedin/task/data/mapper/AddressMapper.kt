package com.convertedin.task.data.mapper

import com.convertedin.task.data.model.AddressDto
import com.convertedin.task.domain.model.Address

fun AddressDto.toAddress() = Address(
    street = street,
    suite = suite,
    city = city,
    zipCode = zipCode
)