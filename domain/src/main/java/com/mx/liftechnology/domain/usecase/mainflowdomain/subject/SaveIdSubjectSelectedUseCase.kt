package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

class SaveIdSubjectSelectedUseCase (
    private val preference: PreferenceUseCase
){
    suspend fun invoke(id: Int?) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP,
            id?:-1
        )
    }
}