/**
 * @file Define el modelo de dominio para el resultado de la búsqueda de una escuela.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.registerschool

import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain


/**
 * Modelo de datos que representa el resultado de la búsqueda de una escuela en la capa de dominio.
 * Contiene tanto los datos de la escuela como los datos necesarios para poblar los spinners del formulario.
 *
 * @property spinners Los datos necesarios para los spinners del formulario de registro de escuela.
 * @property result Los datos brutos de la escuela recibidos de la API.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ResultSchoolDomain(
    val spinners :SchoolSpinnerDomain,
    val result : CCTDomain
)
