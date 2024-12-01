package com.mx.liftechnology.core.model.ModelApi

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("user")
    val user: User?
)

data class User(
    @SerializedName("name")
    val name: String?,
    @SerializedName("paterno")
    val lastName: String?,
    @SerializedName("materno")
    val secondLastName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("user_id")
    val userID: Int?,
    @SerializedName("profesor_id")
    val teacherID: Int?,
    @SerializedName("alumno_id")
    val studentID: Int?,
    @SerializedName("role")
    val role: String?,
)