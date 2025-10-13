package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial

/**
 * Data model for a partial group dialog in the domain layer.
 *
 * @property partialId The unique identifier for the partial.
 * @property startDate The start date of the partial.
 * @property endDate The end date of the partial.
 * @property name The name of the partial.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelDialogGroupPartialDomain(
    val partialId: Int,
    val startDate: String,
    val endDate: String,
    val name: String
)

/**
 * Extension property to convert a list of [ResponseGetPartial] to a list of [ModelDialogGroupPartialDomain].
 * 
 * @receiver A nullable list of [ResponseGetPartial] objects.
 * @return A list of [ModelDialogGroupPartialDomain] objects.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
val List<ResponseGetPartial?>?.ListPartialToConvertModelDialogGroupPartialDomains: List<ModelDialogGroupPartialDomain>
    get() = this?.mapIndexedNotNull { index, partial ->
        partial?.let {
            ModelDialogGroupPartialDomain(
                partialId = it.partialCycleGroupId,
                startDate = it.startDate,
                endDate = it.endDate,
                name = "Parcial ${index+1}",
            )
        }
    } ?: emptyList()