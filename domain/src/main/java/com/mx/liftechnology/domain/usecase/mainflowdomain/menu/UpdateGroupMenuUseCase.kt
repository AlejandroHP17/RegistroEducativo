package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain

/**
 * Use case for updating the selected group in user preferences.
 *
 * @property preference The use case for managing user preferences.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class UpdateGroupMenuUseCase(
    private val preference: PreferenceUseCase
)  {
    /**
     * Updates the selected group in preferences.
     *
     * @param nameItem The [ModelDialogStudentGroupDomain] object representing the selected group.
     */
    operator fun invoke(nameItem: ModelDialogStudentGroupDomain) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP,
            nameItem.item?.teacherSchoolCycleGroupId
        )
    }
}