package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain


class UpdatePartialUseCase(
    private val preference: PreferenceUseCase,
) {

    operator fun invoke(partial: ModelDialogGroupPartialDomain?) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP,
            partial?.partialId?: -1
        )
        val result = ("${partial?.startDate}/${partial?.endDate}")
        preference.savePreferenceString(ModelPreference.RANGE_DATES_PARTIAL, result)
    }

}