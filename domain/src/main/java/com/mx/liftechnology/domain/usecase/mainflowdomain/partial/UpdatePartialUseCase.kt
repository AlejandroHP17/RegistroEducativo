package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase


class UpdatePartialUseCase(
    private val preference: PreferenceUseCase,
) {

    suspend operator fun invoke(itemId: Int) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP,
            itemId
        )
    }

}