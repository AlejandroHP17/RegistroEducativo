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
import com.mx.liftechnology.domain.model.formativeFields.ModelByFieldTypeStudentData
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldData
import com.mx.liftechnology.domain.model.formativeFields.ModelGetListByFieldStudentData
import com.mx.liftechnology.domain.model.formativeFields.ModelGetListByFieldTypeStudentData
import com.mx.liftechnology.domain.model.evaluation.ModelListWorkFormativeField
import com.mx.liftechnology.domain.model.evaluation.ModelListWorkStudentFormativeField
import com.mx.liftechnology.domain.model.evaluation.ModelWorkTypeByFormativeField
import com.mx.liftechnology.domain.model.evaluation.ModelWorkTypeDetail
import com.mx.liftechnology.domain.model.evaluation.ModelWorkTypeFormativeField
import com.mx.liftechnology.domain.model.formativeFields.ModelWotyFofiDomain
import com.mx.liftechnology.domain.model.formativeFields.ResponseFormativeFieldsDomain
import com.mx.liftechnology.domain.model.formativeFields.ResponseWorkTypesDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import kotlin.jvm.JvmName

object FormativeFieldMapper {
    /**
     * Convierte una lista de [ResponseGetListFormativeField] a una lista de [FormativeFieldData] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener campos formativos.
     * @return Una lista de objetos [FormativeFieldData] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetListFormativeFieldList")
    fun List<ResponseGetListFormativeField>?.toData(): List<FormativeFieldData> {
        return this?.mapNotNull { formativeField ->
            formativeField?.let {
                FormativeFieldData(
                    name = it.name,
                    code = it.code,
                    formativeFieldID = it.formativeFieldId
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte una lista de [ResponseGetListWorkType] a una lista de [ModelWorkTypeData] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener tipos de trabajo.
     * @return Una lista de objetos [ModelWorkTypeData] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetListWorkTypeList")
    fun List<ResponseGetListWorkType>?.toData(): List<WorkTypeDomain> {
        return this?.mapNotNull { workType ->
            workType?.let {
                WorkTypeDomain(
                    workTypeId = it.workTypeId,
                    name = it.name
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte un [ResponseFormativeFieldBulk] a [FormativeFieldData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar campos formativos en bulk.
     * @return Un objeto [FormativeFieldData] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseFormativeFieldBulk?.toData(): FormativeFieldData? {
        return this?.let {
            FormativeFieldData(
                name = formativeFieldsName,
                code = formativeFieldsCode,
                formativeFieldID = formativeFieldsId
            )
        }
    }

    /**
     * Convierte un [ResponseGetListWotyFofi] a [ModelWotyFofiDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener la lista de tipos de trabajo por campo formativo.
     * @return Un objeto [ModelWotyFofiDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetListWotyFofi?.toData(): ModelWotyFofiDomain? {
        return this?.let {
            ModelWotyFofiDomain(
                formativeFields = formativeFields?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseWorkTypes] a [ResponseWorkTypesDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para tipos de trabajo.
     * @return Un objeto [ResponseWorkTypesDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseWorkTypes?.toData(): ResponseWorkTypesDomain? {
        return this?.let {
            ResponseWorkTypesDomain(
                workTypeId = workTypeId,
                workTypeName = workTypeName,
                evaluationWeight = evaluationWeight
            )
        }
    }

    /**
     * Convierte un [ResponseFormativeFields] a [ResponseFormativeFieldsDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para campos formativos.
     * @return Un objeto [ResponseFormativeFieldsDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseFormativeFields?.toData(): ResponseFormativeFieldsDomain? {
        return this?.let {
            ResponseFormativeFieldsDomain(
                formativeFieldId = formativeFieldId,
                formativeFieldName = formativeFieldName,
                code = code,
                listWorkTypes = listWorkTypes?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseGetWorkType] a [ModelWorkTypeByFormativeField] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener tipo de trabajo por campo formativo.
     * @return Un objeto [ModelWorkTypeByFormativeField] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetWorkType?.toData(): ModelWorkTypeByFormativeField? {
        return this?.let {
            ModelWorkTypeByFormativeField(
                formativeFieldName = formativeFieldName,
                formativeFieldId = formativeFieldId,
                workTypes = workTypes?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseWorkTypeDetail] a [ModelWorkTypeDetail] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para detalles de tipo de trabajo.
     * @return Un objeto [ModelWorkTypeDetail] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseWorkTypeDetail?.toData(): ModelWorkTypeDetail? {
        return this?.let {
            ModelWorkTypeDetail(
                workTypeName = workTypeName,
                workTypeId = workTypeId,
                evaluationWeight = evaluationWeight
            )
        }
    }

    /**
     * Convierte un [ResponseGetListWorkStudents] a [ModelWorkTypeFormativeField] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista de trabajos de estudiantes.
     * @return Un objeto [ModelWorkTypeFormativeField] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetListWorkStudents?.toData(): ModelWorkTypeFormativeField? {
        return this?.let {
            ModelWorkTypeFormativeField(
                formativeFieldId = formativeFieldId,
                nameFormativeField = nameFormativeField,
                listWorks = listWorks?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseListWork] a [ModelListWorkFormativeField] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para lista de trabajos.
     * @return Un objeto [ModelListWorkFormativeField] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseListWork?.toData(): ModelListWorkFormativeField? {
        return this?.let {
            ModelListWorkFormativeField(
                workId = workId,
                workName = workName,
                listWorks = listWorks?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseListWorkStudent] a [ModelListWorkStudentFormativeField] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para lista de trabajos de estudiantes.
     * @return Un objeto [ModelListWorkStudentFormativeField] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseListWorkStudent?.toData(): ModelListWorkStudentFormativeField? {
        return this?.let {
            ModelListWorkStudentFormativeField(
                workStudentId = workStudentId,
                workStudentName = workStudentName,
                workStudentDate = workStudentDate
            )
        }
    }

    /**
     * Convierte un [ResponseGetByFieldTypeStudent] a [ModelByFieldTypeStudentData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos por tipo de campo y estudiante.
     * @return Un objeto [ModelByFieldTypeStudentData] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetByFieldTypeStudent?.toData(): ModelByFieldTypeStudentData? {
        return this?.let {
            ModelByFieldTypeStudentData(
                formativeFieldId = formativeFieldId,
                formativeFieldName = formativeFieldName,
                workTypeId = workTypeId,
                workTypeName = workTypeName,
                works = works?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseGetListByFieldTypeStudent] a [ModelGetListByFieldTypeStudentData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista por tipo de campo y estudiante.
     * @return Un objeto [ModelGetListByFieldTypeStudentData] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseGetListByFieldTypeStudent?.toData(): ModelGetListByFieldTypeStudentData? {
        return this?.let {
            ModelGetListByFieldTypeStudentData(
                workId = workId,
                workName = workName,
                workDate = workDate,
                listStudents = listStudents?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseGetListByFieldStudent] a [ModelGetListByFieldStudentData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista por campo y estudiante.
     * @return Un objeto [ModelGetListByFieldStudentData] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseGetListByFieldStudent?.toData(): ModelGetListByFieldStudentData? {
        return this?.let {
            ModelGetListByFieldStudentData(
                studentId = studentId,
                studentName = studentName,
                grade = grade
            )
        }
    }
}