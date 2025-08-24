package com.mx.liftechnology.domain.usecase.mainflowdomain.partial

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class SavePartialUseCase (
    private val preference: PreferenceUseCase
)  {
    operator fun invoke (
        listPartial :
        List<ModelDialogGroupPartialDomain>?
    ): ModelDialogGroupPartialDomain?  {

        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val element = listPartial?.firstOrNull { item ->
            val start = LocalDate.parse(item.startDate, formatter)
            val end = LocalDate.parse(item.endDate, formatter)
            currentDate in start..end
        } ?: listPartial?.lastOrNull()

        element?.let {
            val result = ("${it.startDate}/${it.endDate}")
            preference.savePreferenceString(ModelPreference.RANGE_DATES_PARTIAL, result)
            preference.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, it.partialId)
        }?:{
            preference.savePreferenceString(ModelPreference.RANGE_DATES_PARTIAL, null)
            preference.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_PARTIAL_CYCLE_GROUP, -1)
        }
        println("El elemento")
        println(element)
        return element
    }
}