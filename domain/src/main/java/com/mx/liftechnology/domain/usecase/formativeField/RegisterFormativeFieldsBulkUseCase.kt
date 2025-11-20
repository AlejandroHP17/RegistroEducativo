package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.network.apiCall.formativeField.RequestEvaluations
import com.mx.liftechnology.core.network.apiCall.formativeField.RequestRegisterFormativeField
import com.mx.liftechnology.core.network.apiCall.formativeField.RequestWorkType
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.formativeField.ModelFormativeFieldData
import com.mx.liftechnology.data.repository.formativeField.RegisterFormativeFieldsBulkRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.ModelSpinnersWorkMethods

/**
 * @file Define el caso de uso para registrar una nueva materia.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para registrar una única materia.
 * Encapsula la lógica de negocio para construir la petición de registro y manejar la respuesta del repositorio.
 *
 * @property registerFormativeFieldsBulkRepository El repositorio para las operaciones de registro de materias.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterFormativeFieldsBulkUseCase(
    private val registerFormativeFieldsBulkRepository: RegisterFormativeFieldsBulkRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso de registro de una materia.
     *
     * @param updatedList La lista de métodos de trabajo y sus porcentajes.
     * @param name El nombre de la materia.
     * @return Un [ModelResult] que indica el resultado de la operación de registro.
     */
    suspend operator fun invoke(
        updatedList: MutableList<ModelSpinnersWorkMethods>?,
        name: String
    ): ModelResult<ModelFormativeFieldData, Error> {
        val partialId= preference.getPreferenceInt(ModelPreference.ID_PARTIAL)
        val cycleSchoolId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(cycleSchoolId == null || partialId == null || updatedList.isNullOrEmpty()) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val workTypes : MutableList<RequestWorkType> = mutableListOf()
        val evaluations : MutableList<RequestEvaluations> = mutableListOf()

        updatedList.forEach { data ->
            workTypes.add(
                RequestWorkType(
                    workTypeId = data.workTypeId?.let { if(it > 1)data.workTypeId else null },
                    workTypeName = data.name.valueText
                )
            )

            evaluations.add(
                RequestEvaluations(
                    partialId = partialId,
                    workTypeId = data.workTypeId?.let { if(it > 0)data.workTypeId else null },
                    workTypeName = data.name.valueText,
                    evaluationWeight = data.percent.valueText.toInt()
                )
            )
        }

        val request = RequestRegisterFormativeField(
            cycleSchoolId = cycleSchoolId,
            formativeFieldName = name,
            code = name,
            workTypes = workTypes,
            evaluations = evaluations,
        )

        return runCatching {registerFormativeFieldsBulkRepository.executeRegisterFormativeFieldsBulk(request)}.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        SuccessResult(result.data)
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}