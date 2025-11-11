package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseFormativeFieldBulk
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListFormativeField
import com.mx.liftechnology.core.network.apiCall.formativeField.ResponseGetListWorkType
import com.mx.liftechnology.data.model.formativeField.ModelFormativeFieldData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData

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
                name = workType.name,
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
}