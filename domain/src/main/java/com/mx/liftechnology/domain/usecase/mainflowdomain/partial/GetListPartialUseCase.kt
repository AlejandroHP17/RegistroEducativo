/**
 * @file Define el caso de uso para obtener la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetPartial
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.partial.GetListPartialRepository
import com.mx.liftechnology.data.util.ErrorResult as DataErrorResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult as DataSuccessResult
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessResult

/**
 * Caso de uso para obtener la lista de parciales.
 * Encapsula la lógica de negocio para solicitar la lista de parciales, procesarla y manejar los posibles errores.
 *
 * @property getListPartialRepository El repositorio para obtener la lista de parciales desde la fuente de datos.
 * @property preference El caso de uso para gestionar las preferencias del usuario, como IDs de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialUseCase(
    private val getListPartialRepository: GetListPartialRepository,
    private val preference: PreferenceUseCase
)  {
    /**
     * Ejecuta el proceso de obtención de la lista de parciales.
     * Construye la petición, la envía a través del repositorio y transforma la respuesta en un estado de la UI.
     *
     * @return Un [ResultModel] que contiene una lista mutable de [ModelDatePeriodDomain] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ResultModel<MutableList<ModelDatePeriodDomain>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(userId == null || roleId == null || profSchoolCycleGroupId == null) return ErrorResult(ModelCodeError.ERROR_UNKNOWN)

        val request = RequestGetPartial(
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId
        )

        return runCatching {getListPartialRepository.executeGetListPartial(request) }.fold(
            onSuccess = { result ->
                when(result){
                    is DataSuccessResult -> {
                        val listDate = result.data?.mapIndexed { index, item ->
                            ModelDatePeriodDomain(
                                position = index,
                                date = ModelStateOutFieldText(
                                    valueText = "${item?.startDate} / ${item?.endDate}",
                                    isError = false,
                                    errorMessage = ""),
                                partialCycleGroup = item?.partialCycleGroupId!!
                            )
                        } ?.toMutableList()
                        if (listDate?.size!! > 0) {
                            SuccessResult(listDate)
                        }
                        else ErrorResult(ModelCodeError.ERROR_UNKNOWN)
                    }
                    is DataErrorResult -> { handleResponse(result.error) }
                }
            },
            onFailure = {ErrorResult(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Maneja las respuestas de error del repositorio de parciales.
     *
     * @param error El objeto [NetworkError] que representa el error.
     * @return Un [ResultModel] que representa el error específico.
     */
    private fun handleResponse(error: NetworkError): ResultModel<MutableList<ModelDatePeriodDomain>?, String> {
        return when (error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}