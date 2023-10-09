package com.convertedin.task.utils

import android.content.Context

object StringResource {
    private var context :Context? = null

    fun init(context: Context){
        this.context = context
    }

    fun getString(resId:Int):String{
        context?.let {
            return it.getString(resId)
        }
        throw IllegalStateException("ResourceUtils not initialized with application context")
    }
}