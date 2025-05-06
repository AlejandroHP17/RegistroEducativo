package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.ModelDatePeriodDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun interface SavePartialUseCase {
    suspend fun savePartial(
        modelPartial : ModelDialogStudentGroupDomain,
        listPartial : MutableList<ModelDatePeriodDomain>?): ModelDatePeriodDomain?
}
class SavePartialUseCaseImp (
    private val preference: PreferenceUseCase
) : SavePartialUseCase {
    override suspend fun savePartial(
        modelPartial : ModelDialogStudentGroupDomain,
        listPartial : MutableList<ModelDatePeriodDomain>?): ModelDatePeriodDomain?  {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val element =  listPartial?.firstOrNull { item ->
            item.let {
                val dates = it.date.valueText.split("/").map { part -> part.trim() }
                val start = LocalDate.parse(dates[0], formatter)
                val end = LocalDate.parse(dates[1], formatter)
                currentDate in start..end
            }
        }

        element?.let { preference.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, it.partialCycleGroup) }
        return element
    }


}