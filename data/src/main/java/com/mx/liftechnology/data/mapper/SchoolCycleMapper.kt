package com.mx.liftechnology.data.mapper

import com.mx.liftechnology.core.network.api.ResponseCctSchool
import com.mx.liftechnology.core.network.api.ResponseGetPartials
import com.mx.liftechnology.core.network.api.ResponseGroupTeacher
import com.mx.liftechnology.core.network.api.ResponseRegisterPartial
import com.mx.liftechnology.core.network.api.ResponseRegisterSchoolCycle
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTData
import com.mx.liftechnology.data.model.schoolCycle.ModelCCTDataPeriodCatalog
import com.mx.liftechnology.data.model.schoolCycle.ModelListPartialData
import com.mx.liftechnology.data.model.schoolCycle.ModelRegisterSchoolCycleData
import com.mx.liftechnology.data.model.schoolCycle.ModelSchoolCycleData
import kotlin.jvm.JvmName

object SchoolCycleMapper {

    /**
     * Convierte una lista de [ResponseGroupTeacher] a una lista de [ModelSchoolCycleData] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener ciclos escolares.
     * @return Una lista de objetos [ModelSchoolCycleData] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGroupTeacherList")
    fun List<ResponseGroupTeacher>?.toData(): List<ModelSchoolCycleData> {
        return this?.mapNotNull { cycle ->
            cycle?.let {
                ModelSchoolCycleData(
                    teacherId = it.teacherId,
                    schoolId = it.schoolId,
                    name = it.name,
                    grade = it.grade,
                    group = it.groupName,
                    isActive = it.isActive,
                    cycleSchoolId = it.schoolCycleId
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte una lista de [ResponseGetPartials] a una lista de [ModelListPartialData] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para obtener parciales.
     * @return Una lista de objetos [ModelListPartialData] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseGetPartialsList")
    fun List<ResponseGetPartials>?.toData(): List<ModelListPartialData> {
        return this?.mapNotNull { partials ->
            partials?.let {
                ModelListPartialData(
                    description = it.description,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    partialId = it.partialId
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte una lista de [ResponseRegisterPartial] a una lista de [ModelListPartialData] con manejo seguro de nulos.
     *
     * @receiver Una lista de objetos de respuesta de la API para registrar parciales.
     * @return Una lista de objetos [ModelListPartialData] con los datos mapeados. Los elementos nulos son omitidos.
     */
    @JvmName("toDataFromResponseRegisterPartialList")
    fun List<ResponseRegisterPartial>?.toData(): List<ModelListPartialData> {
        return this?.mapNotNull { partials ->
            partials?.let {
                ModelListPartialData(
                    description = it.description,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    partialId = it.partialId
                )
            }
        } ?: emptyList()
    }

    /**
     * Convierte un [ResponseCctSchool] a [ModelCCTData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para obtener datos de CCT de escuela.
     * @return Un objeto [ModelCCTData] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseCctSchool?.toData(): ModelCCTData? {
        return this?.let {
            ModelCCTData(
                id = schoolId,
                cct = cct,
                schoolTypeId = schoolTypeId,
                schoolName = schoolName,
                shiftName = shiftName,
                periodCatalog = periodCatalog?.mapNotNull { catalog ->
                    catalog?.let {
                        ModelCCTDataPeriodCatalog(
                            id = it.id,
                            typeName = it.typeName,
                            periodNumber = it.periodNumber
                        )
                    }
                } ?: emptyList()
            )
        }
    }

    /**
     * Convierte un [ResponseRegisterSchoolCycle] a [ModelRegisterSchoolCycleData] con manejo seguro de nulos.
     *
     * @receiver El objeto de respuesta de la API para registrar ciclo escolar.
     * @return Un objeto [ModelRegisterSchoolCycleData] con los datos mapeados, o null si el receiver es null.
     */
    fun ResponseRegisterSchoolCycle?.toData(): ModelRegisterSchoolCycleData? {
        return this?.let {
            ModelRegisterSchoolCycleData(
                teacherId = teacherId,
                schoolId = schoolId,
                name = name,
                isActive = isActive,
                idCycleSchool = schoolCycleId
            )
        }
    }
}