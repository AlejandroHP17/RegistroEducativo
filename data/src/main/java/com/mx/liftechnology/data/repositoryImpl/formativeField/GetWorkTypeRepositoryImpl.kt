/**
 * @file Define el repositorio para la funcionalidad de obtención de tipos de evaluación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.data.mapper.FormativeFieldMapper.toData
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.util.executeOrError
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.repository.formativeFields.GetWorkTypeRepository


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
    ): ModelResult<List<WorkTypeDomain>, NetworkModelError> {
        return formativeFieldApi.getListWorkType(teacherId).executeOrError { it.toData() }
    }

}