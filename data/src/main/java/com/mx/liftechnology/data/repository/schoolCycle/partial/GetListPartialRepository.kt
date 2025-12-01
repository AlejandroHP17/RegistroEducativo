/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.partial

import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleMapper.toData
import com.mx.liftechnology.data.model.schoolCycle.ModelListPartialData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la obtención de la lista de parciales.
 * Define el contrato para ejecutar la lógica de obtención de la lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListPartialRepository{
  /**
   * Obtiene la lista de parciales.
   *
   * @param schoolCycleId El ID del ciclo escolar.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun getList(
      schoolCycleId : Int
  ): ModelResult<List<ModelListPartialData>, NetworkModelError>
}

/**
 * Implementación de [GetListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListPartialApiCall La llamada a la API para obtener la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi
) : GetListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getList(
        schoolCycleId : Int
    ): ModelResult<List<ModelListPartialData>, NetworkModelError> {
        return schoolCycleApi.getListPartial(schoolCycleId).executeOrError { it.toData() }
    }
}