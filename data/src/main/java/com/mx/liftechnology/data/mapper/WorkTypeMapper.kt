package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseGetListWorkType
import com.mx.liftechnology.core.network.api.ResponseGetWorkType
import com.mx.liftechnology.core.network.api.ResponseWorkTypeDetail
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeDetailDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain

object WorkTypeMapper {
    /**
     * Convierte una lista de [ResponseGetListWorkType] a una lista de [WorkTypeDomain] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener tipos de trabajo.
     * @return Una lista de objetos [WorkTypeDomain] con los datos mapeados. Los elementos nulos son omitidos.
     */
    fun List<ResponseGetListWorkType>.toWorkTypeDomain(): List<WorkTypeDomain> {
        return this.map { workType ->
            workType.let {
                WorkTypeDomain(
                    workTypeId = it.workTypeId,
                    name = it.name
                )
            }
        }
    }

    /**
     * Convierte un [ResponseGetWorkType] a [WorkTypeByFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener tipo de trabajo por campo formativo.
     * @return Un objeto [WorkTypeByFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetWorkType?.toWorkTypeByFormativeFieldDomain(): WorkTypeByFormativeFieldDomain? {
        return this?.let {
            WorkTypeByFormativeFieldDomain(
                formativeFieldName = formativeFieldName,
                formativeFieldId = formativeFieldId,
                workTypes = workTypes.mapNotNull { it.toWorkTypeDetailDomain() }
            )
        }
    }

    /**
     * Convierte un [ResponseWorkTypeDetail] a [WorkTypeDetailDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para detalles de tipo de trabajo.
     * @return Un objeto [WorkTypeDetailDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseWorkTypeDetail?.toWorkTypeDetailDomain(): WorkTypeDetailDomain? {
        return this?.let {
            WorkTypeDetailDomain(
                workTypeName = workTypeName,
                workTypeId = workTypeId,
                evaluationWeight = evaluationWeight
            )
        }
    }
}