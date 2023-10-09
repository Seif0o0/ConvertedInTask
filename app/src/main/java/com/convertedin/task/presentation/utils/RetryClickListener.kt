package com.convertedin.task.presentation.utils

open class RetryClickListener(val clickListener: () -> Unit) {
    fun onRetry() = clickListener()
}