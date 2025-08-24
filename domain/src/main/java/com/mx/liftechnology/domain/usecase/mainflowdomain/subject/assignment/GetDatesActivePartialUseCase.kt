package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

class GetDatesActivePartialUseCase (private val preference: PreferenceUseCase) {

    suspend operator fun invoke() : String? {
        return preference.getPreferenceString(ModelPreference.RANGE_DATES_PARTIAL)
    }
}