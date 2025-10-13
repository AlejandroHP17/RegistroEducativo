package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share

import com.mx.liftechnology.domain.model.registerschool.ModelSpinnerSchoolDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelSpinnerSchoolUi

data class ModelCustomSpinner(
    val value: String?,
    val id: Int?
)


fun String.toModelCustomSpinner() : ModelCustomSpinner{
    return ModelCustomSpinner(
        value = this,
        id = this.toInt()
    )
}

fun ModelSpinnerSchoolDomain?.toUi(): ModelSpinnerSchoolUi {
    return ModelSpinnerSchoolUi(
        cycle = this?.cycle?.map { ModelCustomSpinner(id = 0, value = it) },
        grade = this?.grade?.map { ModelCustomSpinner(id = 0, value = it) },
        group = this?.group?.map { ModelCustomSpinner(id = 0, value = it) }
    )
}

fun List<ModelFormatAssignment>?.toCustomSpinnerList(): List<ModelCustomSpinner>? {
    return this?.map { assignment ->
        ModelCustomSpinner(
            id = assignment.id,
            value = assignment.assignmentName.valueText
        )
    }
}