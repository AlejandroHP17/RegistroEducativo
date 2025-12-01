/**
 * @file Define el repositorio para la funcionalidad de obtención de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repository.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.data.model.formativeField.ModelWorkTypeData
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError

/**
 * Interfaz del repositorio para la obtención de tipos de evaluación.
 * Define el contrato para ejecutar la lógica de obtención de tipos de evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
fun interface GetWorkTypeRepository{
    /**
     * Obtiene la lista de tipos de trabajo.
     *
     * @param teacherId El ID del profesor.
     * @return Un [ModelResult] que indica el resultado de la operación.
     */
    suspend fun getList(teacherId: Int)
            : ModelResult<List<ModelWorkTypeData>, NetworkModelError>
}

/**
 * Implementación de [GetWorkTypeRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property getListWorkTypeApiCall La llamada a la API para obtener la lista de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetWorkTypeRepositoryImpl(
    private val formativeFieldApi: FormativeFieldApi
) : GetWorkTypeRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun getList(
        teacherId: Int
    ): ModelResult<List<ModelWorkTypeData>, NetworkModelError> {
        return formativeFieldApi.getListWorkType(teacherId).executeOrError { it.toData() }
    }

}