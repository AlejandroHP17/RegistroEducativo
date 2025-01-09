package com.mx.liftechnology.core.model.modelApi

import com.google.gson.annotations.SerializedName

data class CctSchool(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombreescuela")
    val nombreescuela: String,
    @SerializedName("tipocicloescolar")
    val tipocicloescolar: String,
    @SerializedName("tipocicloescolar_id")
    val tipocicloescolar_id: Int,
    @SerializedName("tipoescuela")
    val tipoescuela: String,
    @SerializedName("turno")
    val turno: String
)