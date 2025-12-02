package com.mx.liftechnology.domain.model.schoolCycle

/**
 * @file Define el modelo de dominio para un período de fechas.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Modelo de dominio para un período de fechas.
 * Se utiliza para representar un período de tiempo en la lógica de negocio.
 *
 * @property position La posición del período en una lista.
 * @property date El estado del campo de texto de la fecha.
 * @property partialCycleGroup El ID del grupo de ciclo parcial.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelDatePeriod(
    val position: Int,
    val date: String,
    val partialCycleGroup: Int?,
)