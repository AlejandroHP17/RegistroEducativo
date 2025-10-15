package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share

import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText

/**
 * Modelo de datos para una tarjeta de estudiante personalizada en la UI.
 *
 * @property id El identificador único del estudiante.
 * @property numberList El número de lista del estudiante.
 * @property studentName El nombre del estudiante.
 * @property score El estado del campo de texto de la calificación.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelCustomCardStudent(
    val id: String,
    val numberList: String,
    val studentName: String,
    val score: ModelStateOutFieldText,
)
