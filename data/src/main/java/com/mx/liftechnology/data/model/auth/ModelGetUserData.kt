package com.mx.liftechnology.data.model.auth

data class ModelGetUserData(
    val email: String,
    val name: String?,
    val lastName: String?,
    val phone: String?,
    val isActive: Boolean?,
    val userId: Int,
    val accessLevelId: Int?,
)
