package com.mx.liftechnology.core.util

class GenericResponse<T>(
    val data: T?,
    val response: ResponseBasic
)


data class ResponseBasic(
    val code: Int,
    val msg: String
)