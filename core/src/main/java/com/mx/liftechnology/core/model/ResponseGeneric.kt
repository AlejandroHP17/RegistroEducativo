package com.mx.liftechnology.core.model

import com.google.gson.annotations.SerializedName

class ResponseGeneric<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("response")
    val response: ResponseBasic
)

data class ResponseBasic(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String,
    @SerializedName("validation")
    val validation: List<Any>?
)