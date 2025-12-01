package com.mx.liftechnology.data.model.auth

data class LoginData(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String?,
)
