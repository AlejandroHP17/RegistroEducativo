package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGetPartials
import com.mx.liftechnology.core.network.api.ResponseRegisterPartial
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain

object PartialMapper {

    /**
     * Convierte una lista de [ResponseGetPartials] a una lista de [toListPartialDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener parciales.
     * @return Una lista de objetos [toListPartialDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    fun List<ResponseGetPartials>.toListPartialDomain(): List<ListPartialDomain> {
        return this.map { partials ->
            partials.let {
                ListPartialDomain(
                    description = it.description,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    partialId = it.partialId
                )
            }
        }
    }

    /**
     * Convierte una lista de [ResponseRegisterPartial] a una lista de [ListPartialDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para registrar parciales.
     * @return Una lista de objetos [ListPartialDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    fun List<ResponseRegisterPartial>.toListRegisterPartialDomain(): List<ListPartialDomain> {
        return this.map { partials ->
            partials.let {
                ListPartialDomain(
                    description = it.description,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    partialId = it.partialId
                )
            }
        }
    }
}