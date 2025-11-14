package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseFormativeFieldBulk
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseFormativeFields
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListFormativeField
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListWorkType
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListWotyFofi
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetWorkType
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseWorkTypeDetail
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseWorkTypes
import com.mx.liftechnology.data.model.formativeField.ModelFormativeFieldData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeDetail
import com.mx.liftechnology.data.model.formativeField.ModelWotyFofiData
import com.mx.liftechnology.data.model.formativeField.ResponseFormativeFieldsData
import com.mx.liftechnology.data.model.formativeField.ResponseWorkTypesData

object FormativeFieldDataToDomainMapper {
    fun List<ResponseGetListFormativeField>.mapperToModelListFormativeFields(): List<ModelFormativeFieldData>{
        return this.mapIndexed { _, formativeField ->
            ModelFormativeFieldData(
                name = formativeField.name,
                code = formativeField.code,
                formativeFieldID = formativeField.formativeFieldId
            )
        }
    }

    fun List<ResponseGetListWorkType>.mapperToModelListWorkTypeData(): List<ModelWorkTypeData>{
        return this.mapIndexed { _, workType ->
            ModelWorkTypeData(
                workTypeId = workType.workTypeId,
                name = workType.name
            )
        }
    }

    fun ResponseFormativeFieldBulk.mapperToModelListFormativeFields(): ModelFormativeFieldData{
        return ModelFormativeFieldData(
            name = this.formativeFieldsName,
            code = this.formativeFieldsCode,
            formativeFieldID = this.formativeFieldsId
        )
    }

    fun ResponseGetListWotyFofi.mapperToModelListWotyFofi(): ModelWotyFofiData{
        return ModelWotyFofiData(
            // Mapeo de la lista de ResponseFormativeFields a ResponseFormativeFieldsData
            formativeFields = this.formativeFields.map { it.toData() }
        )
    }
    fun ResponseWorkTypes.toData(): ResponseWorkTypesData {
        return ResponseWorkTypesData(
            workTypeId = this.workTypeId,
            workTypeName = this.workTypeName,
            evaluationWeight = this.evaluationWeight
        )
    }

    // --- Extensión para el nivel intermedio ---
    fun ResponseFormativeFields.toData(): ResponseFormativeFieldsData {
        return ResponseFormativeFieldsData(
            formativeFieldId = this.formativeFieldId,
            formativeFieldName = this.formativeFieldName,
            code = this.code,
            listWorkTypes = this.listWorkTypes.map { it.toData() }
        )
    }

    fun ResponseGetWorkType.mapperToModelWorkTypeByFormativeField(): ModelWorkTypeByFormativeField {
        return ModelWorkTypeByFormativeField(
            formativeFieldName = formativeFieldName,
            formativeFieldId = formativeFieldId,
            workTypes = this.workTypes.map { it.toData() }
        )
    }

    fun ResponseWorkTypeDetail.toData(): ModelWorkTypeDetail{
        return ModelWorkTypeDetail(
            workTypeName = this.workTypeName,
            workTypeId = this.workTypeId,
            evaluationWeight = this.evaluationWeight
        )
    }
}