/**
 * @file Define el repositorio para la funcionalidad de obtención de la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.evaluation

import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeFormativeField
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la obtención de la lista de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de la lista de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetListWorkTypeFormativeFieldRepository {
    /**
     * Obtiene la lista de tipos de trabajo por campo formativo.
     *
     * @param formativeFieldId El ID del campo formativo.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(formativeFieldId:Int) : ModelResult<ModelWorkTypeFormativeField, NetworkModelError>
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
    override suspend fun getList(formativeFieldId:Int): ModelResult<ModelWorkTypeFormativeField, NetworkModelError> {
        return evaluationApi.getListWorkTypeStudent(formativeFieldId).executeOrError { it.toData() }
    }
}