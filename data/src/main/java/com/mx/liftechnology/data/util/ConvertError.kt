package com.mx.liftechnology.data.util

fun Error.convertToUI(): UserError{
    return when(this){
        is LocalError ->
            when(this){
                LocalError.USER_INCOMPLETE_DATA -> UserError.SHOW_INCOMPLETE_ERROR
                else -> UserError.LOGS
            }

        is NetworkError ->
            when(this){
                NetworkError.NOT_FOUND -> UserError.SHOW_SPECIFIC_ERROR //404
                NetworkError.UNAUTHORIZED -> UserError.UNAUTHORIZED // 401
                else -> UserError.LOGS
        }
    }
}