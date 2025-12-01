/**
 * @file Define el repositorio para la funcionalidad de registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.schoolCycle.partial

import com.mx.liftechnology.core.network.api.RequestPartials
import com.mx.liftechnology.core.network.api.RequestRegisterPartial
import com.mx.liftechnology.core.network.api.SchoolCycleApi
import com.mx.liftechnology.data.mapper.SchoolCycleDataToDomainMapper.mapperToModelListPartialData
import com.mx.liftechnology.data.model.schoolCycle.ModelDatePeriod
import com.mx.liftechnology.data.model.schoolCycle.ModelListPartialData
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para el registro de una lista de parciales.
 * Define el contrato para ejecutar la lógica de registro de una lista de parciales.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface RegisterListPartialRepository{
  /**
   * Ejecuta la petición de registro de una lista de parciales.
   *
   * @param request Los datos de la petición de registro.
   * @return Un [ModelResult] que indica el resultado de la operación.
   */
  suspend fun executeRegisterListPartial(
      adapterPeriods: List<ModelDatePeriod>,
      cycleSchoolId: Int
  ): ModelResult<List<ModelListPartialData?>, NetworkModelError>
}

/**
 * Implementación de [RegisterListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property registerListPartialApiCall La llamada a la API para el registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialRepositoryImpl(
    private val schoolCycleApi: SchoolCycleApi,
) : RegisterListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun executeRegisterListPartial(
        adapterPeriods: List<ModelDatePeriod>,
        cycleSchoolId: Int
    ): ModelResult<List<ModelListPartialData?>, NetworkModelError> {

        val listAdapter: MutableList<RequestPartials> = mutableListOf()
        adapterPeriods.forEachIndexed { index,  data ->
            val part = data.date.split("/")
            listAdapter.add(
                RequestPartials(
                    cycleSchoolId = cycleSchoolId,
                    description = ("Parcial ${index + 1}"),
                    startDate = part.getOrNull(0)?.trim() ?: "",
                    endDate = part.getOrNull(1)?.trim() ?: "",
                )
            )
        }

        val request = RequestRegisterPartial(listPartials = listAdapter)

        return try {
            val response = schoolCycleApi.registerListPartial(request)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelListPartialData())
                } ?: ErrorResult(NetworkModelError.EMPTY)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}