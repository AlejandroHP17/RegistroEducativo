package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseCctSchool
import com.mx.liftechnology.domain.model.schoolCycle.CCTDomain
import com.mx.liftechnology.domain.model.schoolCycle.CCTPeriodCatalogDomain

object SchoolMapper {
    /**
     * Convierte un [ResponseCctSchool] a [CCTDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos de CCT de escuela.
     * @return Un objeto [CCTDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseCctSchool.toCCTDomain(): CCTDomain {
        return CCTDomain(
            id = schoolId,
            cct = cct,
            schoolTypeId = schoolTypeId,
            schoolName = schoolName,
            shiftName = shiftName,
            periodCatalog = periodCatalog?.mapNotNull { catalog ->
                catalog.let {
                    CCTPeriodCatalogDomain(
                        id = it.id,
                        typeName = it.typeName,
                        periodNumber = it.periodNumber
                    )
                }
            } ?: emptyList()
        )
    }
}