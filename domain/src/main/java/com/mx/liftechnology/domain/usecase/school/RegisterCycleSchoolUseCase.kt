/**
 * @file Define el caso de uso para registrar una nueva escuela asociada a un profesor.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.school


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.RegisterSchoolCycleDomain
import com.mx.liftechnology.domain.repository.school.RegisterCycleSchoolRepository

/**
 * Caso de uso para registrar una nueva escuela y asociarla a un profesor.
 * Encapsula la lógica de negocio para construir la petición de registro y manejar la respuesta del repositorio.
 *
 * @property registerCycleSchoolRepository El repositorio para las operaciones de registro de escuelas.
 * @property preference El caso de uso para la gestión de las preferencias de usuario.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterCycleSchoolUseCase(
    private val registerCycleSchoolRepository: RegisterCycleSchoolRepository,
    private val preference: PreferenceUseCase,
) {

    /**
     * Ejecuta el proceso de registro de la escuela.
     *
     * @param cct La Clave de Centro de Trabajo de la escuela.
     * @param grade El grado que se va a registrar.
     * @param group El nombre del grupo.
     * @param cycle El período del ciclo escolar.
     * @return Un [ModelResult] que indica el resultado de la operación, ya sea un éxito o un error.
     */
    suspend operator fun invoke(
        schoolId: Int,
        periodCatalogId: Int,
        cct: String?,
        grade: Int,
        group: String?,
        cycle: Int,
        shiftName: String,
        labelCycleState : String
    ): ModelResult<RegisterSchoolCycleDomain, ModelError> {
        val teacherId = preference.getIdUser()

        if(schoolId < 1 || periodCatalogId < 1 || grade < 1
            || group.isNullOrEmpty() || cycle < 1 || teacherId == null) return ErrorResult(
            LocalModelError.USER_INCOMPLETE_DATA
        )

        return runCatching { registerCycleSchoolRepository.register(
            teacherId = teacherId,
            schoolId = schoolId,
            name = "Ciclo $labelCycleState, $grade$group, $cct",
            cycleLabel =  labelCycleState.trim(),
            grade = grade.toString(),
            nameGroup = group.trim(),
            periodCatalogId =periodCatalogId,
            )
        }.fold(
            onSuccess = { result ->
                when (result) {
                    is SuccessResult -> {
                        SuccessResult(result.data)
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