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
            UserDomain(
                email = email,
                name = name,
                lastName = lastName,
                phone = phone,
                isActive = isActive,
                userId = userId,
                accessLevelId = accessLevelId
            )
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
        LoginDomain(
            accessToken = accessToken,
            refreshToken = refreshToken,
            tokenType = tokenType
        )
    }

}

/**
 * Convierte un [ResponseRegisterUser] a [RegisterUserDomain] con manejo seguro de nulos.
 *
 * @receiver El objeto de respuesta de la API para registrar usuario.
 * @return Un objeto [RegisterUserDomain] con los datos mapeados, o null si el receiver es null o si faltan campos requeridos.
 */
fun ResponseRegisterUser?.toData(): RegisterUserDomain? {
    return this?.let {
        RegisterUserDomain(
            email = email,
            firstName = firstName,
            lastName = lastName,
            accessLevel = accessLevel,
            isActive = isActive,
            userId = id
        )
    }
}