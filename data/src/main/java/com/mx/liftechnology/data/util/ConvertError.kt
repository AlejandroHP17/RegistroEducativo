package com.mx.liftechnology.data.util

fun ModelError.convertToUI(): UserError{
    return when(this){
        is LocalModelError ->
            when(this){
                LocalModelError.USER_INCOMPLETE_DATA -> UserError.SHOW_INCOMPLETE_ERROR
                else -> UserError.LOGS
            }

        is NetworkModelError ->
            when(this){
                NetworkModelError.NOT_FOUND -> UserError.SHOW_SPECIFIC_ERROR //404
                NetworkModelError.UNAUTHORIZED -> UserError.UNAUTHORIZED // 401
                else -> UserError.LOGS
        }
    }
}