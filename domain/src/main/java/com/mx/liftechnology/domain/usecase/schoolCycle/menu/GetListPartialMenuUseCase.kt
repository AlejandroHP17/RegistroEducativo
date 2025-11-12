/**
 * @file Define el caso de uso para obtener la lista de parciales del menú.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.menu

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.schoolCycle.partial.GetListPartialRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.NetworkError
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.ListPartialToConvertModelDialogGroupPartialDomains
import com.mx.liftechnology.domain.model.schoolCycle.ModelDialogGroupPartialDomain

/**
 * Caso de uso para obtener la lista de parciales del menú.
 * Encapsula la lógica de negocio para solicitar la lista de parciales, procesarla y manejar los posibles errores.
 *
 * @property getListPartialRepository El repositorio para obtener la lista de parciales desde la fuente de datos.
 * @property preference El caso de uso para gestionar las preferencias del usuario, como IDs de sesión.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListPartialMenuUseCase (
    private val getListPartialRepository: GetListPartialRepository,
    private val preference: PreferenceUseCase
)  {
    /**
     * Ejecuta el proceso para obtener la lista de parciales.
     * Construye la petición, la envía a través del repositorio y transforma la respuesta en un estado de la UI.
     *
     * @return Un [ModelResult] que contiene la lista de parciales ([ModelDialogGroupPartialDomain]) en caso de éxito,
     * o un estado de error específico en caso de fallo.
     */
     suspend operator fun invoke(): ModelResult<List<ModelDialogGroupPartialDomain>, Error> {
        val cycleSchoolId = preference.getPreferenceInt(ModelPreference.ID_CYCLE_SCHOOL)

        if(cycleSchoolId == null) return ErrorResult(LocalError.USER_INCOMPLETE_DATA)

         return runCatching { getListPartialRepository.executeGetListPartial(cycleSchoolId) }.fold(
             onSuccess = { result ->
                 when (result){
                     is SuccessResult -> {
                         val convertedResult = result.data.ListPartialToConvertModelDialogGroupPartialDomains
                         if (convertedResult.isNotEmpty()) {
                             SuccessResult(convertedResult)
                         }
                         else ErrorResult(LocalError.EMPTY)
                     }
                     is ErrorResult -> {
                         ErrorResult(result.error)
                     }
                 }
             },
             onFailure = { ErrorResult(NetworkError.UNKNOWN)}
         )
    }
}