package com.mx.liftechnology.registroeducativo.model.dataclass

data class ModelStudentForm(
    val name : String?,
    val lastName : String?,
    val secondLastName : String?,
    val curp: String?,
    val birthday : List<Int>?,
    val phoneNumber : Long?,
)
