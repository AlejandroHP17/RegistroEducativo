package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.apiCall.flowMain.RequestGetListSubject
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.subject.GetListSubjectRepository
import com.mx.liftechnology.data.util.ErrorResult as DataErrorResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult as DataSuccessResult
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.model.subject.toModelSubjectList

/**
 * @file Define el caso de uso para obtener la lista de materias.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Caso de uso para obtener la lista de materias.
 * Encapsula la lógica de negocio para solicitar la lista de materias, procesarla y manejar los errores.
 *
 * @property getListSubjectRepository El repositorio para obtener la lista de materias.
 * @property preference El caso de uso para gestionar las preferencias del usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectUseCase (
    private val getListSubjectRepository : GetListSubjectRepository,
    private val preference: PreferenceUseCase
) {
    /**
     * Ejecuta el proceso para obtener la lista de materias.
     *
     * @return Un [ResultModel] que contiene la lista de materias o un estado de error.
     */
    suspend operator fun invoke(): ResultModel<List<ModelFormatSubjectDomain>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_USER_LEVEL)
        val pecg= preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        val request = RequestGetListSubject(
            teacherId = roleId,
            userId = userId,
            teacherSchoolCycleGroupId = pecg
        )

        return runCatching { getListSubjectRepository.executeGetListSubject(request) }.fold(
            onSuccess = { result ->
                when(result){
                    is DataSuccessResult -> {
                        if (result.data.isNullOrEmpty()) ErrorUserResult(ModelCodeError.ERROR_DATA)
                        else SuccessResult(result.data?.toModelSubjectList())
                    }
                    is DataErrorResult -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = {ErrorResult(ModelCodeError.ERROR_UNKNOWN)}
        )
    }

    /**
     * Maneja las respuestas de error del repositorio, convirtiendo un [NetworkError] en un [ResultModel] específico.
     *
     * @param error El objeto [NetworkError] que representa el error de la capa de datos.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: NetworkError): ResultModel<List<ModelFormatSubjectDomain>?, String> {
        return when (error) {
            NetworkError.BAD_REQUEST -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.UNAUTHORIZED -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            NetworkError.NOT_FOUND -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            NetworkError.TIMEOUT -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }

}