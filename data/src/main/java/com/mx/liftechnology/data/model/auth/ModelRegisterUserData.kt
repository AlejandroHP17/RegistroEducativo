package com.mx.liftechnology.data.model.auth

data class ModelRegisterUserData(
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val accessLevel: Int,
    val isActive: Boolean?,
    val userId: Int
)
