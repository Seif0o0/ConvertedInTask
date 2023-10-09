package com.convertedin.task.utils

import android.app.Application


object StringResource {
    private var context: Application? = null

    fun init(context: Application) {
        this.context = context
    }

    fun getString(resId: Int): String {
        context?.let {
            return it.getString(resId)
        }
        throw IllegalStateException("ResourceUtils not initialized with application context")
    }
}