package com.mx.liftechnology.data.model.auth

data class ModelLoginData(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String?,
)
