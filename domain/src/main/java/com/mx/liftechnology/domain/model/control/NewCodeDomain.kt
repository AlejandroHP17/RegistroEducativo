package com.mx.liftechnology.domain.model.control

data class NewCodeDomain(
    val code: String,
    val accessLevelId: Int,
    val description: String
)