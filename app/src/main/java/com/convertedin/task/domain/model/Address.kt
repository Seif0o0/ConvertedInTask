package com.convertedin.task.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipCode: String
) : Parcelable