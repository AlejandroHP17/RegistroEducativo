package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseDataUser
import com.mx.liftechnology.core.network.api.ResponseLogin
import com.mx.liftechnology.core.network.api.ResponseRegisterUser
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.model.auth.LoginData
import com.mx.liftechnology.data.model.auth.ModelRegisterUserData

object AuthMapper {
    /**
     * Convierte un [ResponseDataUser] a [ModelGetUserData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos de usuario.
     * @return Un objeto [ModelGetUserData] con los datos mapeados, o null si el receiver es null o si faltan campos requeridos.
     */
    fun ResponseDataUser?.toData(): ModelGetUserData? {
        return this?.let {
            val emailValue = email
            val userIdValue = userId
            if (emailValue != null && userIdValue != null) {
                ModelGetUserData(
                    email = emailValue,
                    name = name,
                    lastName = lastName,
                    phone = phone,
                    isActive = isActive,
                    userId = userIdValue,
                    accessLevelId = accessLevelId
                )
            } else {
                null
            }
        }
    }

    /**
     * Convierte un [ResponseLogin] a [LoginData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para login.
     * @return Un objeto [LoginData] con los datos mapeados, o null si el receiver es null o si faltan campos requeridos.
     */
    fun ResponseLogin?.toData(): LoginData? {
        return this?.let {
            val accessToken = accessToken
            val refreshToken = refreshToken
            if (accessToken != null && refreshToken != null) {
                LoginData(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    tokenType = tokenType
                )
            } else {
                null
            }
        }
    }

    /**
     * Convierte un [ResponseRegisterUser] a [ModelRegisterUserData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar usuario.
     * @return Un objeto [ModelRegisterUserData] con los datos mapeados, o null si el receiver es null o si faltan campos requeridos.
     */
    fun ResponseRegisterUser?.toData(): ModelRegisterUserData? {
        return this?.let {
            val emailValue = email
            val userIdValue = id
            val accessLevelValue = accessLevel
            if (emailValue != null && userIdValue != null && accessLevelValue != null) {
                ModelRegisterUserData(
                    email = emailValue,
                    firstName = firstName,
                    lastName = lastName,
                    accessLevel = accessLevelValue,
                    isActive = isActive,
                    userId = userIdValue
                )
            } else {
                null
            }
        }
    }
}