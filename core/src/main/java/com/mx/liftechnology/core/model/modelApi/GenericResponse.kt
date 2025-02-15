package com.mx.liftechnology.core.model.modelApi

import com.google.gson.annotations.SerializedName

class GenericResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("response")
    val response: ResponseBasic
)

data class ResponseBasic(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("validation")
    val validation: List<Any>?
)