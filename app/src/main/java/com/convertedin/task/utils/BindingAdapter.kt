package com.convertedin.task.utils

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.bumptech.glide.Glide
import com.convertedin.task.R
import com.convertedin.task.domain.model.Address
import com.google.android.material.textview.MaterialTextView


@BindingAdapter("image_url")
fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background).into(this)
}

@BindingAdapter("user_address")
fun MaterialTextView.setUserAddress(address: Address) {
    val fullAddress =
        "${address.city}, ${address.street} ${context.getString(R.string.street)}, ${address.suite} (${address.zipCode})"
    text = fullAddress
}

@BindingAdapter("loading_status")
fun View.setLoadingStatus(isLoading: Boolean) {
    visibility = if (isLoading) View.VISIBLE
    else View.GONE
}

@BindingAdapter("error_status")
fun ConstraintLayout.setErrorStatus(isError: Boolean) {
    visibility = if (isError) View.VISIBLE
    else View.GONE
}

@BindingAdapter("error_status", "error_message")
fun LottieAnimationView.setErrorStatus(isError: Boolean, errorMessage: String) {
    if (isError) {
        setAnimation(if (errorMessage == context.getString(R.string.no_internet_connection)) "no_internet_connection.json" else "error_dialog_animation.json")
        playAnimation()
        repeatCount = LottieDrawable.INFINITE
    } else {
        cancelAnimation()
    }
}

@BindingAdapter("loading_status")
fun LottieAnimationView.setLoadingStatus(isLoading: Boolean) {
    if (isLoading) {
        visibility = View.VISIBLE
        setAnimation("progress_bar.json")
        playAnimation()
        repeatCount = LottieDrawable.INFINITE
    } else {
        visibility = View.GONE
        cancelAnimation()
    }
}