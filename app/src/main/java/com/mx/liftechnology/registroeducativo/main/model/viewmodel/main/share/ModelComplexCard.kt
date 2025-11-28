package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share

data class ModelComplexCard( //formativeFields
    val idTitle : Int?,
    val nameTitle : String?,
    val isShowTitle: Boolean = false,
    val isExpandedTitle : Boolean = false,
    val list : List<ModelSubComplexCard?>?,
)

data class ModelSubComplexCard( //work
    val idSubTitle : Int?,
    val nameSubTitle : String?,
    val isShowSubTitle: Boolean = false,
    val isExpandedSubTitle : Boolean = false,
    val list : List<ModelSubSubComplexCard?>?,
)

data class ModelSubSubComplexCard( //evaluation
    val idDescription : Int,
    val nameDescription : String?,
    val grade : Double?,
    val isShowDescription: Boolean = false
)