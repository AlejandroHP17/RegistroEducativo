package com.mx.liftechnology.domain.model.registerschool

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseCctSchool

data class ModelResultSchoolDomain(
    val spinners : ModelSpinnerSchoolDomain,
    val result :  ResponseCctSchool?
)