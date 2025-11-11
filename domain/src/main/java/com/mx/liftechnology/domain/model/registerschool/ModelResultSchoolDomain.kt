package com.mx.liftechnology.domain.model.registerschool

import com.mx.liftechnology.data.model.schoolCycle.ModelCCTData

/**
 * Modelo de datos que representa el resultado de la búsqueda de una escuela en la capa de dominio.
 *
 * @property spinners Los datos necesarios para los spinners del formulario de registro de escuela.
 * @property result Los datos brutos de la escuela recibidos de la API.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelResultSchoolDomain(
    val spinners :ModelSpinnerSchoolDomain,
    val result :  ModelCCTData
)
