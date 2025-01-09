package com.mx.liftechnology.core.model.modelApi

class GenericResponse<T>(
    val data: T?,
    val response: ResponseBasic
)

data class ResponseBasic(
    val code: Int,
    val msg: String,
    val validation: List<Any>?
)