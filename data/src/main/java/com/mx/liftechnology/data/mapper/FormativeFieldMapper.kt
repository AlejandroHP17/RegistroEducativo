package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseFormativeFieldBulk
import com.mx.liftechnology.core.network.api.ResponseFormativeFields
import com.mx.liftechnology.core.network.api.ResponseGetByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListFormativeField
import com.mx.liftechnology.core.network.api.ResponseGetListWorkStudents
import com.mx.liftechnology.core.network.api.ResponseGetListWorkType
import com.mx.liftechnology.core.network.api.ResponseGetListWotyFofi
import com.mx.liftechnology.core.network.api.ResponseGetWorkType
import com.mx.liftechnology.core.network.api.ResponseListWork
import com.mx.liftechnology.core.network.api.ResponseListWorkStudent
import com.mx.liftechnology.core.network.api.ResponseWorkTypeDetail
import com.mx.liftechnology.core.network.api.ResponseWorkTypes
import com.mx.liftechnology.data.model.formativeField.ModelByFieldTypeStudentData
import com.mx.liftechnology.data.model.formativeField.FormativeFieldData
import com.mx.liftechnology.data.model.formativeField.ModelGetListByFieldStudentData
import com.mx.liftechnology.data.model.formativeField.ModelGetListByFieldTypeStudentData
import com.mx.liftechnology.data.model.formativeField.ModelListWorkFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelListWorkStudentFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeByFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeDetail
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWotyFofiData
import com.mx.liftechnology.data.model.formativeField.ResponseFormativeFieldsData
import com.mx.liftechnology.data.model.formativeField.ResponseWorkTypesData

object FormativeFieldMapper {
    fun List<ResponseGetListFormativeField>.mapperToModelListFormativeFields(): List<FormativeFieldData>{
        return this.mapIndexed { _, formativeField ->
            FormativeFieldData(
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

    fun ResponseFormativeFieldBulk.mapperToModelListFormativeFields(): FormativeFieldData{
        return FormativeFieldData(
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

    fun ResponseGetListWorkStudents.mapperToModelWorkTypeFormativeField() : ModelWorkTypeFormativeField {
        return ModelWorkTypeFormativeField(
            formativeFieldId = this.formativeFieldId,
            nameFormativeField = this.nameFormativeField,
            listWorks = this.listWorks.map {
                it.toData()
            }
        )
    }

    fun ResponseListWork.toData():ModelListWorkFormativeField{
        return ModelListWorkFormativeField(
            workId = this.workId,
            workName = this.workName,
            listWorks = this.listWorks.map { it.toData() }
        )
    }

    fun ResponseListWorkStudent.toData():ModelListWorkStudentFormativeField{
        return ModelListWorkStudentFormativeField(
            workStudentId = this.workStudentId,
            workStudentName = this.workStudentName,
            workStudentDate = this.workStudentDate
        )
    }

    fun ResponseGetByFieldTypeStudent.mapperToModelByFieldTypeStudentData () : ModelByFieldTypeStudentData{
        return ModelByFieldTypeStudentData(
            formativeFieldId = this.formativeFieldId,
            formativeFieldName = this.formativeFieldName,
            workTypeId = this.workTypeId,
            workTypeName = this.workTypeName,
            works = this.works.map { it.toData() }
        )
    }

    private fun ResponseGetListByFieldTypeStudent.toData() : ModelGetListByFieldTypeStudentData{
        return ModelGetListByFieldTypeStudentData(
            workId = this.workId,
            workName = this.workName,
            workDate = this.workDate,
            listStudents = this.listStudents.map { it.toData() }
        )
    }

    private fun ResponseGetListByFieldStudent.toData() : ModelGetListByFieldStudentData {
        return ModelGetListByFieldStudentData(
            studentId = this.studentId,
            studentName = this.studentName,
            grade = this.grade
        )
    }
}