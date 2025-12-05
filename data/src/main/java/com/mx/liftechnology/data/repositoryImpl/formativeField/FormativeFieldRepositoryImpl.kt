package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.RequestEvaluations
import com.mx.liftechnology.core.network.api.RequestRegisterFormativeField
import com.mx.liftechnology.core.network.api.RequestWorkType
import com.mx.liftechnology.core.network.api.WorkTypeApi
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toDomain
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toFormativeFieldDomain
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toWotyFofiDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository

/**
 * Implementación de [FormativeFieldRepository].
 * Se encarga de realizar las llamadas a la API y de gestionar las respuestas de éxito y error
 * para todas las operaciones relacionadas con campos formativos.
 *
 * @property formativeFieldApi La llamada a la API para operaciones de campos formativos.
 * @property workTypeApi La llamada a la API para operaciones de tipos de trabajo.
 * @author Pelkidev
 * @version 2.0.0
 */
class FormativeFieldRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi,
    private val workTypeApi: WorkTypeApi
) : FormativeFieldRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getList(
        cycleSchoolId: Int
    ): ModelResult<List<FormativeFieldDomain>, NetworkModelError> {
        return safeApiCall(
            apiCall = { formativeFieldApi.getListFormativeFields(cycleSchoolId) },
            mapper = { it.toFormativeFieldDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun getListWotyFofi(schoolCycleId: Int): ModelResult<WotyFofiDomain, NetworkModelError> {
        return safeApiCall(
            apiCall = { formativeFieldApi.getListWotyFofi(schoolCycleId) },
            mapper = { it.toWotyFofiDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun registerBulk(
        cycleSchoolId: Int,
        formativeFieldName: String,
        code: String,
        workTypes: List<RequestWorkType>,
        evaluations: List<RequestEvaluations>
    ): ModelResult<FormativeFieldDomain, NetworkModelError> {
        val request = RequestRegisterFormativeField(
            cycleSchoolId = cycleSchoolId,
            formativeFieldName = formativeFieldName,
            code = code,
            workTypes = workTypes,
            evaluations = evaluations,
        )

        return safeApiCall(
            apiCall = { formativeFieldApi.registerFormativeFieldsBulk(request) },
            mapper = { it.toFormativeFieldDomain() }
        )
    }

    /**
     * {@inheritDoc}
     */
    override suspend fun delete(fieldId: Int): ModelResult<String, NetworkModelError> {
        return safeApiCall(
            apiCall = { formativeFieldApi.deleteFormativeField(fieldId) },
            mapper = { it.toDomain() }
        )
    }
}
