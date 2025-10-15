/**
 * @file Define el modelo de dominio para los parciales en un diálogo y su función de mapeo.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.model.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetPartial

/**
 * Modelo de datos de dominio para un parcial en un diálogo de selección.
 * Representa la información de un parcial que se muestra en la UI.
 *
 * @property partialId El identificador único del parcial.
 * @property startDate La fecha de inicio del parcial.
 * @property endDate La fecha de fin del parcial.
 * @property name El nombre descriptivo del parcial (ej: "Parcial 1").
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelDialogGroupPartialDomain(
    val partialId: Int?,
    val startDate: String,
    val endDate: String,
    val name: String
)

/**
 * Propiedad de extensión para convertir una lista de [ResponseGetPartial] (modelo de red)
 * a una lista de [ModelDialogGroupPartialDomain] (modelo de dominio).
 *
 * @receiver Una lista nulable de objetos [ResponseGetPartial].
 * @return Una lista de objetos [ModelDialogGroupPartialDomain]. Si la lista de entrada es nula o contiene elementos nulos, estos son omitidos.
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
                name = "Parcial ${index + 1}",
            )
        }
    } ?: emptyList()