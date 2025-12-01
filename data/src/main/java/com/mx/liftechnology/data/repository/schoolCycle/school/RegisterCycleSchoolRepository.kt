/**
 * @file Define el repositorio para la funcionalidad de registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.school

import com.mx.liftechnology.core.network.api.RequestRegisterSchoolCycle
import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.data.model.schoolCycle.ModelRegisterSchoolCycleData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para el registro de escuelas.
 * Define el contrato para ejecutar la lógica de registro de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterCycleSchoolRepository{
  /**
   * Registra un ciclo escolar.
   *
   * @param teacherId El ID del profesor.
   * @param schoolId El ID de la escuela.
   * @param name El nombre del ciclo.
   * @param cycleLabel La etiqueta del ciclo.
   * @param grade El grado.
   * @param nameGroup El nombre del grupo.
   * @param periodCatalogId El ID del catálogo de períodos.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun register(
      teacherId : Int,
      schoolId : Int,
      name : String,
      cycleLabel : String,
      grade : String,
      nameGroup : String,
      periodCatalogId : Int
  ): ModelResult<ModelRegisterSchoolCycleData, NetworkModelError>
}

/**
 * Implementación de [RegisterCycleSchoolRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerSchoolCycleApiCall La llamada a la API para el registro de escuelas.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterCycleSchoolRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
) : RegisterCycleSchoolRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun register(
        teacherId : Int,
        schoolId : Int,
        name : String,
        cycleLabel : String,
        grade : String,
        nameGroup : String,
        periodCatalogId : Int
    ): ModelResult<ModelRegisterSchoolCycleData, NetworkModelError> {
        val request = RequestRegisterSchoolCycle(
            teacherId = teacherId,
            schoolId = schoolId,
            name = name,
            cycleLabel = "",
            grade = grade,
            nameGroup = nameGroup,
            periodCatalogId = periodCatalogId,
            isActive = true
        )

        return schoolCycleApi.registerSchoolCycle(request).executeOrError { it.toData() }
    }
}