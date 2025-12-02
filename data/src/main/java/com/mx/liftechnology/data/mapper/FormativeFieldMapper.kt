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
import com.mx.liftechnology.domain.model.formativeFields.ByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldData
import com.mx.liftechnology.domain.model.formativeFields.GetListByFieldStudentDomain
import com.mx.liftechnology.domain.model.formativeFields.GetListByFieldTypeStudentDomain
import com.mx.liftechnology.domain.model.evaluation.ListWorkFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.ListWorkStudentFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeDetailDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeFormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WotyFofiDomain
import com.mx.liftechnology.domain.model.formativeFields.ListFormativeFieldsDomain
import com.mx.liftechnology.domain.model.formativeFields.ListWorkTypesDomain
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
     * Convierte un [ResponseGetListWotyFofi] a [WotyFofiDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener la lista de tipos de trabajo por campo formativo.
     * @return Un objeto [WotyFofiDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetListWotyFofi?.toData(): WotyFofiDomain? {
        return this?.let {
            WotyFofiDomain(
                formativeFields = formativeFields?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseWorkTypes] a [ListWorkTypesDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para tipos de trabajo.
     * @return Un objeto [ListWorkTypesDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseWorkTypes?.toData(): ListWorkTypesDomain? {
        return this?.let {
            ListWorkTypesDomain(
                workTypeId = workTypeId,
                workTypeName = workTypeName,
                evaluationWeight = evaluationWeight
            )
        }
    }

    /**
     * Convierte un [ResponseFormativeFields] a [ListFormativeFieldsDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para campos formativos.
     * @return Un objeto [ListFormativeFieldsDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseFormativeFields?.toData(): ListFormativeFieldsDomain? {
        return this?.let {
            ListFormativeFieldsDomain(
                formativeFieldId = formativeFieldId,
                formativeFieldName = formativeFieldName,
                code = code,
                listWorkTypes = listWorkTypes?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseGetWorkType] a [WorkTypeByFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener tipo de trabajo por campo formativo.
     * @return Un objeto [WorkTypeByFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetWorkType?.toData(): WorkTypeByFormativeFieldDomain? {
        return this?.let {
            WorkTypeByFormativeFieldDomain(
                formativeFieldName = formativeFieldName,
                formativeFieldId = formativeFieldId,
                workTypes = workTypes?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseWorkTypeDetail] a [WorkTypeDetailDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para detalles de tipo de trabajo.
     * @return Un objeto [WorkTypeDetailDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseWorkTypeDetail?.toData(): WorkTypeDetailDomain? {
        return this?.let {
            WorkTypeDetailDomain(
                workTypeName = workTypeName,
                workTypeId = workTypeId,
                evaluationWeight = evaluationWeight
            )
        }
    }

    /**
     * Convierte un [ResponseGetListWorkStudents] a [WorkTypeFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista de trabajos de estudiantes.
     * @return Un objeto [WorkTypeFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetListWorkStudents?.toData(): WorkTypeFormativeFieldDomain? {
        return this?.let {
            WorkTypeFormativeFieldDomain(
                formativeFieldId = formativeFieldId,
                nameFormativeField = nameFormativeField,
                listWorks = listWorks?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseListWork] a [ListWorkFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para lista de trabajos.
     * @return Un objeto [ListWorkFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseListWork?.toData(): ListWorkFormativeFieldDomain? {
        return this?.let {
            ListWorkFormativeFieldDomain(
                workId = workId,
                workName = workName,
                listWorks = listWorks?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseListWorkStudent] a [ListWorkStudentFormativeFieldDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para lista de trabajos de estudiantes.
     * @return Un objeto [ListWorkStudentFormativeFieldDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseListWorkStudent?.toData(): ListWorkStudentFormativeFieldDomain? {
        return this?.let {
            ListWorkStudentFormativeFieldDomain(
                workStudentId = workStudentId,
                workStudentName = workStudentName,
                workStudentDate = workStudentDate
            )
        }
    }

    /**
     * Convierte un [ResponseGetByFieldTypeStudent] a [ByFieldTypeStudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos por tipo de campo y estudiante.
     * @return Un objeto [ByFieldTypeStudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseGetByFieldTypeStudent?.toData(): ByFieldTypeStudentDomain? {
        return this?.let {
            ByFieldTypeStudentDomain(
                formativeFieldId = formativeFieldId,
                formativeFieldName = formativeFieldName,
                workTypeId = workTypeId,
                workTypeName = workTypeName,
                works = works?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseGetListByFieldTypeStudent] a [GetListByFieldTypeStudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista por tipo de campo y estudiante.
     * @return Un objeto [GetListByFieldTypeStudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseGetListByFieldTypeStudent?.toData(): GetListByFieldTypeStudentDomain? {
        return this?.let {
            GetListByFieldTypeStudentDomain(
                workId = workId,
                workName = workName,
                workDate = workDate,
                listStudents = listStudents?.mapNotNull { it.toData() } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseGetListByFieldStudent] a [GetListByFieldStudentDomain] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener lista por campo y estudiante.
     * @return Un objeto [GetListByFieldStudentDomain] con los datos mapeados, o null si el receiver es null.
     */
    private fun ResponseGetListByFieldStudent?.toData(): GetListByFieldStudentDomain? {
        return this?.let {
            GetListByFieldStudentDomain(
                studentId = studentId,
                studentName = studentName,
                grade = grade
            )
        }
    }
}