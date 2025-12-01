/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.mapperToModelWorkTypeFormativeField
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeFormativeField
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkException
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.SuccessResult
import retrofit2.HttpException

/**
 * Interfaz del repositorio para la obtención de la lista de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListWorkTypeFormativeFieldRepository {
    /**
     * Ejecuta la petición para obtener la lista de tipos de evaluación.
     *
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun executeGetListWorkTypeFormativeField(formativeFieldId:Int) : ModelResult<ModelWorkTypeFormativeField, NetworkModelError>
}

/**
 * Implementación de [GetListWorkTypeFormativeFieldRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListWorkTypeStudentApiCall La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListWorkTypeFormativeFieldRepositoryImpl (
    private val evaluationApi: EvaluationApi
): GetListWorkTypeFormativeFieldRepository{
    /**
     * {@inheritDoc}
     */
    override suspend fun executeGetListWorkTypeFormativeField(formativeFieldId:Int): ModelResult<ModelWorkTypeFormativeField, NetworkModelError> {
        return try {
            val response = evaluationApi.getListWorkTypeStudent(formativeFieldId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.data?.let {
                    SuccessResult(it.mapperToModelWorkTypeFormativeField())
                } ?: ErrorResult(NetworkModelError.EMPTY)
            } else {
                ErrorResult(NetworkException.handleException(HttpException(response)))
            }
        } catch (e: Exception) {
            ErrorResult(NetworkException.handleException(e))
        }
    }
}