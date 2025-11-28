package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.domain.model.evaluation.ModelCardDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCardStudent

object EvaluationUIToDomainMapper {
    fun List<ModelCustomCardStudent>.toModelCard()  : List<ModelCardDomain>{
        return this.map { student ->
            ModelCardDomain(
                studentId = student.id.toIntOrNull() ?: 0,
                grade = student.score.valueText.toDoubleOrNull(),
            )
        }
    }
}