package com.mx.liftechnology.domain.repository.formativeFields

import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldData

/**
 * Interfaz del repositorio para el registro de materias.
 * Define el contrato para ejecutar la lógica de registro de materias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterFormativeFieldsBulkRepository{
    /**
     * Registra una materia formativa en bulk.
     *
     * @param cycleSchoolId El ID del ciclo escolar.
     * @param formativeFieldName El nombre de la materia formativa.
     * @param code El código de la materia.
     * @param workTypes La lista de tipos de trabajo.
     * @param evaluations La lista de evaluaciones.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun registerBulk(
        cycleSchoolId : Int,
        formativeFieldName : String,
        code : String,
        workTypes : List<RequestWorkType>,
        evaluations :  List<RequestEvaluations>
    ): ModelResult<FormativeFieldData, NetworkModelError>
}