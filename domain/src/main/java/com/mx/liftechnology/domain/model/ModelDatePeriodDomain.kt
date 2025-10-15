/**
 * @file Define el modelo de dominio para un período de fechas.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

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
data class ModelDatePeriodDomain(
    val position: Int,
    val date: ModelStateOutFieldText,
    val partialCycleGroup: Int?,
)
