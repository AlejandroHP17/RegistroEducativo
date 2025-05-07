package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.callapi.ResponseGetPartial

data class ModelDialogGroupPartialDomain(
    val partialId: Int,
    val startDate: String,
    val endDate: String,
    val name: String
)

val List<ResponseGetPartial?>?.ListPartialToConvertModelDialogGroupPartialDomains: List<ModelDialogGroupPartialDomain>
    get() = this?.mapIndexedNotNull { index, partial -> // Filtramos los valores nulos
        partial?.let {
            ModelDialogGroupPartialDomain(
                partialId = it.partialCycleGroup,
                startDate = it.startDate,
                endDate = it.endDate,
                name = "Parcial ${index+1}",
            )
        }
    } ?: emptyList()