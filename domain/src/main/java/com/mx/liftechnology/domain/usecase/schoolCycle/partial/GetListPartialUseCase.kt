/**
 * @file Define el caso de uso para obtener la lista de parciales.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.partial


import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.repository.schoolCycle.partial.GetListPartialRepository

/**
 * Caso de uso para obtener la lista de parciales.
 * Encapsula la lógica de negocio para solicitar la lista de parciales, procesarla y manejar los posibles errores.
 *
 * @property getListPartialRepository El repositorio para obtener la lista de parciales desde la fuente de datos.
 * @property preference El caso de uso para gestionar las preferencias del usuario, como IDs de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialUseCase(
    private val getListPartialRepository: GetListPartialRepository,
    private val preference: PreferenceUseCase
)  {
    /**
     * Ejecuta el proceso de obtención de la lista de parciales.
     * Construye la petición, la envía a través del repositorio y transforma la respuesta en un estado de la UI.
     *
     * @return Un [ModelResult] que contiene una lista mutable de [DatePeriodDomain] en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
    suspend operator fun invoke(): ModelResult<MutableList<DatePeriodDomain>?, ModelError> {
        val cycleSchoolId= preference.getIdCycleSchool()
        if(cycleSchoolId == null) return ErrorResult(LocalModelError.USER_INCOMPLETE_DATA)

        val result = getListPartialRepository.getList(cycleSchoolId)
        return when (result) {
            is SuccessResult -> {
                val listDate = result.data.mapIndexed { index, item ->
                    DatePeriodDomain(
                        position = index,
                        date = ModelStateOutFieldText(
                            valueText = "${item.startDate} / ${item.endDate}",
                            isError = false,
                            errorMessage = ""),
                        partialCycleGroup = item.partialId
                    )
                }.toMutableList()
                if (listDate.isNotEmpty()) {
                    SuccessResult(listDate)
                } else {
                    ErrorResult(LocalModelError.EMPTY)
                }
            }
            is ErrorResult -> result
        }
    }
}