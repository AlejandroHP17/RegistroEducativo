package com.mx.liftechnology.domain.usecase.mainflowdomain.school

import android.os.Build
import com.mx.liftechnology.core.network.apiCall.flowMain.RequestRegisterSchool
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.school.RegisterSchoolRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import java.util.Calendar
import java.util.Date

class RegisterOneSchoolUseCase(
    private val registerSchoolRepository: RegisterSchoolRepository,
    private val preference: PreferenceUseCase,
) {

    suspend operator fun invoke(
        cct: String?,
        schoolCycleTypeId: Int?,
        grade: Int?,
        group: String?,
        cycle: Int?,
    ): ModelState<List<String?>?, String> {
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
                        SuccessState(result.data)
                    }

                    is ResultError -> {
                        handleResponse(result.error)
                    }
                }
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_UNKNOWN) }
        )
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<List<String?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION_REGISTER_USER)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}

