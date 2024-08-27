package com.mx.liftechnology.core.model

/** Model - Include all the elements to control
 * @author pelkidev
 * @since 1.0.0
 */
data class ModelAddress(
    val street: String?,
    val extNumbber: String?,
    val intNumber: String?,
    val neighborhood: String?,
    val cp: String?,
    val municipality: String?,
    val state: String?
)
