package com.mx.liftechnology.domain.usecase.flowregisterdata

import com.mx.liftechnology.core.model.modelApi.CctSchool
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainFlow.RegisterSchoolRepository


fun interface RegisterSchoolUseCase {
    suspend fun putNewSchool(result: CctSchool?, grade: Int?, group: String?, cycle: Int?)
}

class RegisterSchoolUseCaseImp(
    private val registerSchoolRepository: RegisterSchoolRepository,
    private val preference: PreferenceUseCase
): RegisterSchoolUseCase {

    /** Validate Email
     * @author pelkidev
     * @since 1.0.0
     * */
    override suspend fun putNewSchool(
        result: CctSchool?,
        grade: Int?,
        group: String?,
        cycle: Int?
    ) {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)

        registerSchoolRepository.executeRegisterSchool(
            result,
            grade!!,
            group!!,
            cycle!!,
            userId ,
            roleId)
    }
}

