/**
 * @file Define el repositorio para la funcionalidad de registro de materias.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestRegisterFormativeField
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldData
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.repository.formativeFields.RegisterFormativeFieldsBulkRepository

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

        return safeApiCall(
            apiCall = { formativeFieldApi.registerFormativeFieldsBulk(request) },
            mapper = { it.toData() }
        )
    }
}