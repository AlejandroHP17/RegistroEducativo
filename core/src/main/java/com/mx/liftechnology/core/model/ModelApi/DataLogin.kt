package com.mx.liftechnology.core.model.ModelApi

data class Data(
    val access_token: String,
    val expires_in: Int,
    val token_type: String,
    val user: User
)

data class User(
    val email: String,
    val materno: Any,
    val name: String,
    val paterno: Any,
    val role: String,
    val user_id: Int
)