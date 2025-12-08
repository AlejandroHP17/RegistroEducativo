package com.mx.liftechnology.domain.model.formativeFields

/**
 * Modelo de dominio que representa la estructura de campos formativos con sus tipos de trabajo asociados.
 * Este modelo agrupa los campos formativos de un ciclo escolar con sus respectivos tipos de trabajo.
 *
 * @property formativeFields La lista de campos formativos con sus tipos de trabajo asociados.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class WotyFofiDomain(
    val formativeFields: List<ListFormativeFieldsDomain>,
)

/**
 * Modelo de dominio que representa un campo formativo con su lista de tipos de trabajo.
 *
 * @property formativeFieldId El ID único del campo formativo.
 * @property formativeFieldName El nombre del campo formativo.
 * @property code El código del campo formativo.
 * @property listWorkTypes La lista de tipos de trabajo asociados al campo formativo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListFormativeFieldsDomain(
    val formativeFieldId: Int,
    val formativeFieldName: String,
    val code: String,
    val listWorkTypes: List<ListWorkTypesDomain>
)

/**
 * Modelo de dominio que representa un tipo de trabajo asociado a un campo formativo.
 *
 * @property workTypeId El ID único del tipo de trabajo.
 * @property workTypeName El nombre del tipo de trabajo.
 * @property evaluationWeight El peso de evaluación del tipo de trabajo (porcentaje).
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ListWorkTypesDomain(
    val workTypeId: Int,
    val workTypeName: String,
    val evaluationWeight: String
)