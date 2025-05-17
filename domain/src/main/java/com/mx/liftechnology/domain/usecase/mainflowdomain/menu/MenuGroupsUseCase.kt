package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain

class UpdateGroupMenuUseCase(
    private val preference: PreferenceUseCase
)  {
    /** Update the group in preferences
     * @author Alejandro Hernandez Pelcastre
     * @since 1.0.0
     */
    operator fun invoke(nameItem: ModelDialogStudentGroupDomain) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP,
            nameItem.item?.teacherSchoolCycleGroupId
        )
    }
}