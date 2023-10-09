package com.convertedin.task.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto(
    val street: String,
    val suite: String,
    val city: String,
    @Json(name = "zipcode") val zipCode: String
)