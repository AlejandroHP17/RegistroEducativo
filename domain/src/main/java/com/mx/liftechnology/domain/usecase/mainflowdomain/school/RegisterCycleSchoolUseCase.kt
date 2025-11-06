/**
 * @file Define el caso de uso para registrar una nueva escuela asociada a un profesor.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import android.os.Build
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterCycleSchool
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.ModelRegisterCycleData
import com.mx.liftechnology.data.repository.flowMain.school.RegisterCycleSchoolRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ResultModel
import java.util.Calendar
import java.util.Date

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
     * @return Un [ResultModel] que indica el resultado de la operación, ya sea un éxito o un error.
     */
    suspend operator fun invoke(
        schoolId: Int,
        periodCatalogId: Int,
        cct: String?,
        grade: Int,
        group: String?,
        cycle: Int,
        shiftName: String
    ): ModelResult<ModelRegisterCycleData, Error> {

        if(schoolId < 1 || periodCatalogId < 1 || grade < 1
            || group.isNullOrEmpty() || cycle < 1) return ErrorResult(
            LocalError.USER_INCOMPLETE_DATA
        )

        val teacherId = preference.getPreferenceInt(ModelPreference.ID_USER)
        val buildDate = Date(Build.TIME)
        val calendar = Calendar.getInstance().apply { time = buildDate }
        val year = calendar[Calendar.YEAR]

        val request = RequestRegisterCycleSchool(
            teacherId = teacherId,
            schoolId = schoolId,
            name = "$cct Grupo: $grade $group, $shiftName",
            description = "$cct Grupo: $grade $group, $shiftName",
            year = year,
            cycleLabel = "",
            grade = grade.toString(),
            nameGroup = group,
            periodCatalogId =periodCatalogId,
            isActive = true
        )
        return runCatching { registerCycleSchoolRepository.executeRegisterCycleSchool(request) }.fold(
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
            onFailure = { ErrorResult(NetworkError.UNKNOWN)}
        )
    }
}