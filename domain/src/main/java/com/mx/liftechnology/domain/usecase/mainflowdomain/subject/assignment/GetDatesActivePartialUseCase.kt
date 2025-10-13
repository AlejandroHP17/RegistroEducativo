package com.mx.liftechnology.domain.usecase.mainflowdomain.subject.assignment

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * Use case for getting the active partial's date range.
 *
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetDatesActivePartialUseCase (private val preference: PreferenceUseCase) {

    /**
     * Executes the process of getting the active partial's date range.
     *
     * @return A string representing the date range, or null if not found.
     */
    suspend operator fun invoke() : String? {
        return preference.getPreferenceString(ModelPreference.RANGE_DATES_PARTIAL)
    }
}