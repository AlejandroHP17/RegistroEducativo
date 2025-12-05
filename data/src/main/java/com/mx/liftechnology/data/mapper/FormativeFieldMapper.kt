package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseFormativeFieldBulk
import com.mx.liftechnology.core.network.api.ResponseFormativeFields
import com.mx.liftechnology.core.network.api.ResponseGetListFormativeField
import com.mx.liftechnology.core.network.api.ResponseGetListWorkType
import com.mx.liftechnology.core.network.api.ResponseGetListWotyFofi
import com.mx.liftechnology.core.network.api.ResponseGetWorkType
import com.mx.liftechnology.core.network.api.ResponseWorkTypeDetail
import com.mx.liftechnology.core.network.api.ResponseWorkTypes
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeDetailDomain
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.domain.model.formativeFields.ListFormativeFieldsDomain
import com.mx.liftechnology.domain.model.formativeFields.ListWorkTypesDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import kotlin.jvm.JvmName

object FormativeFieldMapper {
    /**
     * Convierte una lista de [ResponseGetListFormativeField] a una lista de [FormativeFieldDomainPar] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener campos formativos.
     * @return Una lista de objetos [FormativeFieldDomainPar] con los datos mapeados. Los elementos nulos son omitidos.
     */
    fun List<ResponseGetListFormativeField>.toFormativeFieldDomain(): List<FormativeFieldDomain> {
        return this.map { formativeField ->
            formativeField.let {
                FormativeFieldDomain(
                    name = it.name,
                    code = it.code,
                    formativeFieldID = it.formativeFieldId
                )
            }
        }
    }

    /**
     * Convierte un [ResponseGetListWotyFofi] a [WotyFofiDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener la lista de tipos de trabajo por campo formativo.
     * @return Un objeto [WotyFofiDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetListWotyFofi.toWotyFofiDomain(): WotyFofiDomain {
        return WotyFofiDomain(
            formativeFields = formativeFields.mapNotNull { it.toListFormativeFieldsDomain() }
        )
    }

    /**
     * Convierte un [ResponseFormativeFields] a [ListFormativeFieldsDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para campos formativos.
     * @return Un objeto [ListFormativeFieldsDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseFormativeFields?.toListFormativeFieldsDomain(): ListFormativeFieldsDomain? {
        return this?.let {
            ListFormativeFieldsDomain(
                formativeFieldId = formativeFieldId,
                formativeFieldName = formativeFieldName,
                code = code,
                listWorkTypes = listWorkTypes.mapNotNull { it.toListWorkTypesDomain() }
            )
        }
    }

    /**
     * Convierte un [ResponseWorkTypes] a [ListWorkTypesDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para tipos de trabajo.
     * @return Un objeto [ListWorkTypesDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseWorkTypes?.toListWorkTypesDomain(): ListWorkTypesDomain? {
        return this?.let {
            ListWorkTypesDomain(
                workTypeId = workTypeId,
                workTypeName = workTypeName,
                evaluationWeight = evaluationWeight
            )
        }
    }

    /**
     * Convierte un [ResponseFormativeFieldBulk] a [FormativeFieldDomainPar] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar campos formativos en bulk.
     * @return Un objeto [FormativeFieldDomainPar] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseFormativeFieldBulk.toFormativeFieldDomain(): FormativeFieldDomain {
        return FormativeFieldDomain(
            name = formativeFieldsName,
            code = formativeFieldsCode,
            formativeFieldID = formativeFieldsId
        )
    }

    fun String.toDomain(): String{
        return this
    }
}