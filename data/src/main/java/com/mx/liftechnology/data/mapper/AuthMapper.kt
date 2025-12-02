package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseDataUser
import com.mx.liftechnology.core.network.api.ResponseLogin
import com.mx.liftechnology.core.network.api.ResponseRegisterUser
import com.mx.liftechnology.domain.model.auth.LoginDomain
import com.mx.liftechnology.domain.model.auth.RegisterUserDomain
import com.mx.liftechnology.domain.model.auth.UserDomain

object AuthMapper {
    /**
     * Convierte un [ResponseDataUser] a [UserDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos de usuario.
     * @return Un objeto [UserDomain] con los datos mapeados, o null si el receiver es null o si faltan campos requeridos.
     */
    fun ResponseDataUser?.toData(): UserDomain? {
        return this?.let {
            val emailValue = email
            val userIdValue = userId
            if (emailValue != null && userIdValue != null) {
                UserDomain(
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
     * Convierte un [ResponseLogin] a [LoginDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para login.
     * @return Un objeto [LoginDomain] con los datos mapeados, o null si el receiver es null o si faltan campos requeridos.
     */
    fun ResponseLogin?.toData(): LoginDomain? {
        return this?.let {
            val accessToken = accessToken
            val refreshToken = refreshToken
            if (accessToken != null && refreshToken != null) {
                LoginDomain(
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
    fun ResponseRegisterUser?.toData(): RegisterUserDomain? {
        return this?.let {
            val emailValue = email
            val userIdValue = id
            val accessLevelValue = accessLevel
            if (emailValue != null && userIdValue != null && accessLevelValue != null) {
                RegisterUserDomain(
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