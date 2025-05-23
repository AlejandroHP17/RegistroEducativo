package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.network.callapi.CredentialsRegisterSubject
import com.mx.liftechnology.core.network.callapi.Percent
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainflowdata.subject.CrudSubjectRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods


interface RegisterOneSubjectUseCase {
    suspend fun registerOneSubject(
        updatedList: MutableList<ModelFormatSubjectDomain>?,
        name: String?
    ): ModelState<List<String?>?, String>?
    suspend fun registerOneSubjectCompose(
        updatedList: MutableList<ModelSpinnersWorkMethods>?,
        name: String?
    ): ModelState<List<String?>?, String>?
}

class RegisterOneSubjectUseCaseImp(
    private val crudSubjectRepository: CrudSubjectRepository,
    private val preference: PreferenceUseCase
): RegisterOneSubjectUseCase {

    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun registerOneSubject(
        updatedList: MutableList<ModelFormatSubjectDomain>?,
        name: String?
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<Percent> = mutableListOf()
        updatedList?.forEach { data ->
            listAdapter.add(
                Percent(
                    jobId = -1,
                    percent = data.percent?.toInt(),
                    assessmentType = data.name
                )
            )
        }

        val request = CredentialsRegisterSubject(
            subject = name,
            options = updatedList?.size,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId,
            percents = listAdapter
        )

        return when (val result =  crudSubjectRepository.executeRegisterOneSubject(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    override suspend fun registerOneSubjectCompose(
        updatedList: MutableList<ModelSpinnersWorkMethods>?,
        name: String?
    ): ModelState<List<String?>?, String> {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)
        val profSchoolCycleGroupId= preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)

        val listAdapter: MutableList<Percent> = mutableListOf()
        updatedList?.forEach { data ->
            listAdapter.add(
                Percent(
                    jobId = data.assessmentTypeId,
                    percent = data.percent.valueText.toInt(),
                    assessmentType = data.name.valueText
                )
            )
        }

        val request = CredentialsRegisterSubject(
            subject = name,
            options = updatedList?.size,
            teacherSchoolCycleGroupId = profSchoolCycleGroupId,
            userId = userId,
            teacherId = roleId,
            percents = listAdapter
        )

        return when (val result =  crudSubjectRepository.executeRegisterOneSubject(request)) {
            is ResultSuccess -> {
                SuccessState(result.data)
            }
            is ResultError -> {
                handleResponse(result.error)
            }
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }

    /** handleResponse - Validate the code response, and assign the correct function of that
     * @author pelkidev
     * @since 1.0.0
     * if not return the correct error
     * @return ModelState
     */
    private fun handleResponse(error: FailureService): ModelState<List<String?>?, String> {
        return when (error) {
            is FailureService.BadRequest -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Unauthorized -> ErrorUnauthorizedState(ModelCodeError.ERROR_UNAUTHORIZED)
            is FailureService.NotFound -> ErrorUserState(ModelCodeError.ERROR_VALIDATION)
            is FailureService.Timeout -> ErrorState(ModelCodeError.ERROR_TIMEOUT)
            else -> ErrorState(ModelCodeError.ERROR_UNKNOWN)
        }
    }
}
