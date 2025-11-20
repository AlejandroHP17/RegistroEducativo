package com.mx.liftechnology.data.model.evaluation

data class ModelWorkTypeEvaluations(
    val updatedWorks: List<CreatedWorks>,
    val totalStudentsWithGrade: Int,
    val totalStudentsWithoutGrade: Int,
    val workTypeId: Int,
    val nameWork: String
)

data class CreatedWorks(
    val studentId: Int,
    val grade: Double,
    val workDate: String?,
    val workId: Int,
)
