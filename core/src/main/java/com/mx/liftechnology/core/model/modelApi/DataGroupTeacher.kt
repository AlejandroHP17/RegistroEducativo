package com.mx.liftechnology.core.model.modelApi

import com.google.gson.annotations.SerializedName

data class DataGroupTeacher(
    @SerializedName("cct")
    val cct: String,
    @SerializedName("cicloescolar_id")
    val schoolYearId: Int,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("escuelaciclo_id")
    val cycleSchoolId: Int,
    @SerializedName("grupo")
    val group: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("nombreescuela")
    val nameSchool: String,
    @SerializedName("profesor_id")
    val teacherId: Int,
    @SerializedName("profesorescuelaciclogrupo_id")
    val teacherSchoolCycleGroupId: Int,
    @SerializedName("tipo")
    val type: String,
    @SerializedName("turno")
    val shift: String
)