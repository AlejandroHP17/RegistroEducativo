/**
 * @file Define el repositorio para la funcionalidad de registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestRegisterFormativeField
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.model.formativeField.FormativeFieldData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

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
    )
    : ModelResult<FormativeFieldData, NetworkModelError>
}

/**
 * Implementación de [RegisterFormativeFieldsBulkRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerFormativeFieldsBulkApiCall La llamada a la API para el registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsBulkRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
) : RegisterFormativeFieldsBulkRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun registerBulk(
        cycleSchoolId : Int,
        formativeFieldName : String,
        code : String,
        workTypes : List<RequestWorkType>,
        evaluations :  List<RequestEvaluations>
    ): ModelResult<FormativeFieldData, NetworkModelError> {
        val request = RequestRegisterFormativeField(
            cycleSchoolId = cycleSchoolId,
            formativeFieldName = formativeFieldName,
            code = code,
            workTypes = workTypes,
            evaluations = evaluations,
        )

        return formativeFieldApi.registerFormativeFieldsBulk(request).executeOrError { it.toData() }
    }
}