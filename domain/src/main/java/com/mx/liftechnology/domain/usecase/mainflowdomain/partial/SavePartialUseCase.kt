package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.log
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface SavePartialUseCase {
    suspend fun savePartial(
        modelPartial : ModelDialogStudentGroupDomain,
        listPartial : List<ModelDialogGroupPartialDomain>?): ModelDialogGroupPartialDomain?

    suspend fun updatePartial(itemId: Int)

    suspend fun test()
}
class SavePartialUseCaseImp (
    private val preference: PreferenceUseCase
) : SavePartialUseCase {
    override suspend fun savePartial(
        modelPartial : ModelDialogStudentGroupDomain,
        listPartial :
        List<ModelDialogGroupPartialDomain>?): ModelDialogGroupPartialDomain?  {

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val element =  listPartial?.firstOrNull { item ->
            item.let {
                val start = LocalDate.parse(item.startDate, formatter)
                val end = LocalDate.parse(item.endDate, formatter)
                currentDate in start..end
            }
        }

        element?.let { preference.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, it.partialId) }?:
        preference.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, -1)
        return element
    }


    override suspend fun updatePartial(itemId: Int) {
        preference.savePreferenceInt(
            ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP,
            itemId
        )
    }

    override suspend fun test() {
        log(preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP).toString(), "ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP")
        log(preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP).toString(), "ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP")
    }


}