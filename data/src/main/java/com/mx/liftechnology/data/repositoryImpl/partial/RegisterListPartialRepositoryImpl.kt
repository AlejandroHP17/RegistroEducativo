/**
 * @file Define el repositorio para la funcionalidad de registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.data.repositoryImpl.partial

import com.mx.liftechnology.core.network.api.PartialApi
import com.mx.liftechnology.core.network.api.RequestPartials
import com.mx.liftechnology.core.network.api.RequestRegisterPartial
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.data.mapper.PartialMapper.toListRegisterPartialDomain
import com.mx.liftechnology.data.util.safeApiCall
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialDomain
import com.mx.liftechnology.domain.repository.partial.RegisterListPartialRepository


/**
 * Implementación de [RegisterListPartialRepository].
 * Se encarga de realizar la llamada a la API y de gestionar las respuestas de éxito y error.
 *
 * @property PartialApi La llamada a la API para el registro de una lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterListPartialRepositoryImpl(
    private val partialApi: PartialApi,
) : RegisterListPartialRepository {

    /**
     * {@inheritDoc}
     */
    override suspend fun register(
        adapterPeriods: List<DatePeriodDomain>,
        cycleSchoolId: Int
    ): ModelResult<List<ListPartialDomain?>, NetworkModelError> {
        val listAdapter: MutableList<RequestPartials> = mutableListOf()
        adapterPeriods.forEachIndexed { index,  data ->
            val part = data.date.valueText.split("/")
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

        return safeApiCall(
            apiCall = { partialApi.registerListPartial(request) },
            mapper = { it.toListRegisterPartialDomain() }
        )
    }
}