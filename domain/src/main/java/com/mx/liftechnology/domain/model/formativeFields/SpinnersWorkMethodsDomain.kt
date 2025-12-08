/**
 * @file Define el modelo de datos para los métodos de trabajo en un spinner.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.formativeFields

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Modelo de datos para un ítem en un spinner de métodos de trabajo.
 * Representa un método de evaluación con su nombre, porcentaje y IDs asociados.
 *
 * @property position La posición del ítem en la lista.
 * @property workTypeId El ID del tipo de evaluación.
 * @property name El estado del campo de texto para el nombre del método de trabajo.
 * @property percent El estado del campo de texto para el porcentaje del método de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class SpinnersWorkMethodsDomain(
    val position: Int,
    val workTypeId: Int?,
    val name: ModelStateOutFieldText,
    val percent: ModelStateOutFieldText,
)
