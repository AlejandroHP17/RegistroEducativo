/**
 * @file Define el caso de uso para registrar una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.partial

import com.mx.liftechnology.core.network.apiCall.schoolCycle.RequestPartials
import com.mx.liftechnology.core.network.apiCall.schoolCycle.RequestRegisterPartial
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.schoolCycle.ModelListPartialData
import com.mx.liftechnology.data.repository.schoolCycle.partial.RegisterListPartialRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.ModelDatePeriodDomain

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
        adapterPeriods: List<ModelDatePeriodDomain>
    ): ModelResult<List<ModelListPartialData?>, Error> {
        val teacherId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val cycleSchoolId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(teacherId == null || cycleSchoolId == null || adapterPeriods.isEmpty()) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val listAdapter: MutableList<RequestPartials> = mutableListOf()
        adapterPeriods.forEachIndexed { index,  data ->
            val part = data.date.valueText.split("/")
            listAdapter.add(
                RequestPartials(
                    cycleSchoolId = cycleSchoolId,
                    description = ("Parcial $index + 1"),
                    startDate = part.getOrNull(0)?.trim() ?: "",
                    endDate = part.getOrNull(1)?.trim() ?: "",
                )
            )
        }

        val request = RequestRegisterPartial(listPartials = listAdapter)

        return runCatching { registerListPartialRepository.executeRegisterListPartial(request) }.fold(
            onSuccess = { result ->
                when(result){
                    is SuccessResult -> {
                        if(result.data.isNotEmpty()) SuccessResult(result.data)
                        else ErrorResult(NetworkError.UNKNOWN_REGISTER)
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