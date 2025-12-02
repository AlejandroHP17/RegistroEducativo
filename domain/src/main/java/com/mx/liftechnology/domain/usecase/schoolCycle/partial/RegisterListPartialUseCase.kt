/**
 * @file Define el caso de uso para registrar una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.partial


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.schoolCycle.ModelDatePeriod
import com.mx.liftechnology.data.model.schoolCycle.ModelListPartialData
import com.mx.liftechnology.data.repository.schoolCycle.partial.RegisterListPartialRepository
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalModelError
import com.mx.liftechnology.data.util.ModelError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain

/**
 * Caso de uso para registrar una lista de parciales.
 * Encapsula la lógica de negocio para construir la petición y enviarla al repositorio para su registro.
 *
 * @property registerListPartialRepository El repositorio para registrar la lista de parciales.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialUseCase(
    private val registerListPartialRepository: RegisterListPartialRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso de registro de una lista de parciales.
     *
     * @param adapterPeriods La lista de períodos de fechas a registrar.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend operator fun invoke(
        adapterPeriods: List<DatePeriodDomain>
    ): ModelResult<List<ModelListPartialData?>, ModelError> {
        val cycleSchoolId = preference.getIdCycleSchool()

        if(cycleSchoolId == null || adapterPeriods.isEmpty()) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        val adapter = adapterPeriods.map {
            ModelDatePeriod(
                position = it.position,
                date = it.date.valueText,
                partialCycleGroup = it.partialCycleGroup
            )
        }

        return runCatching { registerListPartialRepository.register(
            adapterPeriods = adapter,
            cycleSchoolId = cycleSchoolId
        ) }.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        if(result.data.isNotEmpty()) SuccessResult(result.data)
                        else ErrorResult(NetworkModelError.NOT_FOUND)
                    }
                    is ErrorResult -> {
                        ErrorResult(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(NetworkModelError.UNKNOWN)}
        )
    }
}