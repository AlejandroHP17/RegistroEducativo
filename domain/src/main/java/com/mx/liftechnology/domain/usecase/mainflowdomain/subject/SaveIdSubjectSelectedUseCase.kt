package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase

/**
 * Use case for saving the ID of the selected subject in user preferences.
 *
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SaveIdSubjectSelectedUseCase (
    private val preference: PreferenceUseCase
){
    /**
     * Saves the ID of the selected subject.
     *
     * @param id The ID of the subject to save.
     */
    fun invoke(id: Int?) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP,
            id?:-1
        )
    }
}