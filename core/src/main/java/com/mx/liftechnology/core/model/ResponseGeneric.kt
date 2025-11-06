/**
 * @file Define los modelos de datos para las respuestas genéricas de la API.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.model

import com.google.gson.annotations.SerializedName

/**
 * Clase genérica para las respuestas de la API, que encapsula los datos y la información básica de la respuesta.
 *
 * @param T El tipo de los datos (`data`) que se esperan en la respuesta.
 * @property data Los datos específicos de la respuesta, que pueden ser nulos.
 * @property response La información básica de la respuesta, como el código y el mensaje.
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
 * Modelo de datos para la información básica de una respuesta de la API.
 *
 * @property code El código de respuesta HTTP.
 * @property message El mensaje asociado a la respuesta.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResponseBasic(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)