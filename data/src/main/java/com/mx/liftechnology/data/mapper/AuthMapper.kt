package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseDataUser
import com.mx.liftechnology.core.network.api.ResponseLogin
import com.mx.liftechnology.core.network.api.ResponseRegisterUser
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.model.auth.LoginData
import com.mx.liftechnology.data.model.auth.ModelRegisterUserData

object AuthMapper {
    fun ResponseDataUser.mapperToGetUserData(): ModelGetUserData {
        return ModelGetUserData(
            email = this.email,
            name = this.name,
            lastName = this.lastName,
            phone = this.phone,
            isActive = this.isActive,
            userId = this.userId,
            accessLevelId = this.accessLevelId
        )
    }

    fun ResponseLogin.mapperToGetLoginData(): LoginData{
        return LoginData(
            accessToken = accessToken,
            refreshToken = refreshToken,
            tokenType = tokenType
        )
    }

    fun ResponseRegisterUser.mapperToRegisterUser(): ModelRegisterUserData{
        return ModelRegisterUserData(
            email = email,
            firstName = firstName,
            lastName = lastName,
            accessLevel = accessLevel,
            isActive = isActive,
            userId = id
        )
    }
}