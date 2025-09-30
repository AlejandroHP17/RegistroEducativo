package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share

import com.mx.liftechnology.domain.model.subject.ModelFormatAssignment

data class ModelComplexCard(
    val idTitle : Int?,
    val nameTitle : String?,
    val isShowTitle: Boolean = false,
    val isExpandedTitle : Boolean = false,
    val list : List<ModelSubComplexCard?>?,
)

data class ModelSubComplexCard(
    val idSubTitle : Int?,
    val nameSubTitle : String?,
    val isShowSubTitle: Boolean = false,
    val isExpandedSubTitle : Boolean = false,
    val list : List<ModelSubSubComplexCard?>?,
)

data class ModelSubSubComplexCard(
    val idDescription : String?,
    val nameDescription : String?,
    val isShowDescription: Boolean = false
)

fun List<ModelFormatAssignment>?.toModelSubComplexCard(): List<ModelSubComplexCard?>? {
    return this?.map {
        ModelSubComplexCard(
            idSubTitle = it.id,
            nameSubTitle = it.assignmentName.valueText,
            isShowSubTitle = true,
            isExpandedSubTitle = false,
            list = null,
        )
    }
}