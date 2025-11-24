package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.auth.ResponseDataUser
import com.mx.liftechnology.core.network.apiCall.auth.ResponseLogin
import com.mx.liftechnology.core.network.apiCall.auth.ResponseRegisterUser
import com.mx.liftechnology.data.model.auth.ModelGetUserData
import com.mx.liftechnology.data.model.auth.ModelLoginData
import com.mx.liftechnology.data.model.auth.ModelRegisterUserData

object AuthDataToDomainMapper {
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

    fun ResponseLogin.mapperToGetLoginData(): ModelLoginData{
        return ModelLoginData(
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