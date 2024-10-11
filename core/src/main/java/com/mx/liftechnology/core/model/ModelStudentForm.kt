package com.mx.liftechnology.core.model

/** Model - Include all the elements to control
 * @author pelkidev
 * @since 1.0.0
 */
data class ModelStudentForm(
    val name: String?,
    val lastName: String?,
    val secondLastName: String?,
    val curp: String?,
    val birthday: List<Int>?,
    val phoneNumber: Long?,
)
