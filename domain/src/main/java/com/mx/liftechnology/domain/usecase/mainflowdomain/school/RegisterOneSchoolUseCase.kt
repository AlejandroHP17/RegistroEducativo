/**
 * @file Define el caso de uso para registrar una nueva escuela asociada a un profesor.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import android.os.Build
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSchool
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedResult
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult
import java.util.Calendar
import java.util.Date

/**
 * Caso de uso para registrar una nueva escuela y asociarla a un profesor.
 * Encapsula la lógica de negocio para construir la petición de registro y manejar la respuesta del repositorio.
 *
 * @property registerSchoolRepository El repositorio para las operaciones de registro de escuelas.
 * @property preference El caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterOneSchoolUseCase(
    private val registerSchoolRepository: RegisterSchoolRepository,
    private val preference: PreferenceUseCase,
) {

    /**
     * Ejecuta el proceso de registro de la escuela.
     *
     * @param cct La Clave de Centro de Trabajo de la escuela.
     * @param schoolCycleTypeId El ID del tipo de ciclo escolar.
     * @param grade El grado que se va a registrar.
     * @param group El nombre del grupo.
     * @param cycle El período del ciclo escolar.
     * @return Un [ResultModel] que indica el resultado de la operación, ya sea un éxito o un error.
     */
    suspend operator fun invoke(
        cct: String?,
        schoolCycleTypeId: Int?,
        grade: Int?,
        group: String?,
        cycle: Int?,
    ): ResultModel<List<String?>?, String> {
        val userId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId = preference.getPreferenceInt(ModelPreference.ID_ROLE)

        val buildDate = Date(Build.TIME)
        val calendar = Calendar.getInstance().apply { time = buildDate }
        val year = calendar[Calendar.YEAR]

        val request = RequestRegisterSchool(
            cct = cct,
            typeCycleSchoolId = schoolCycleTypeId,
            grade = grade,
            nameGroup = group,
            year = year.toString(),
            period = cycle,
            teacherId = roleId,
            userId = userId,
        )
        return runCatching { registerSchoolRepository.executeRegisterOneSchool(request) }.fold(
            onSuccess = { result ->
                when (result) {
                    is ResultSuccess -> {
                        SuccessResult(result.data)
                    }

                    is ResultError -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = { ErrorResult(ModelCodeError.ERROR_UNKNOWN) }
        )
    }

    /**
     * Maneja las respuestas de error del repositorio de registro de escuelas.
     *
     * @param error El objeto [FailureService] que representa el error.
     * @return Un [ResultModel] que representa el error específico para la capa de dominio/UI.
     */
    private fun handleResponse(error: FailureService): ResultModel<List<String?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedResult(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserResult(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorResult(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorResult(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}