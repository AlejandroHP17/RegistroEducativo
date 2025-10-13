package com.mx.liftechnology.core.model

import com.google.gson.annotations.SerializedName

/**
 * A generic class for API responses.
 *
 * @param T The type of the data in the response.
 * @property data The data payload of the response.
 * @property response The basic response information.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ResponseGeneric<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("response")
    val response: ResponseBasic
)

/**
 * Data class for basic response information.
 *
 * @property code The response code.
 * @property message The response message.
 * @property validation A list of validation errors, if any.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseBasic(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val message: String,
    @SerializedName("validation")
    val validation: List<Any>?
)