/**
 * @file Define el caso de uso para obtener la lista de ítems del menú de registro y control.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.data.repository.flowMain.menu.MenuLocalRepository
import com.mx.liftechnology.domain.model.generic.ErrorResult
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ResultModel
import com.mx.liftechnology.domain.model.generic.SuccessResult

/**
 * Caso de uso para obtener la lista de ítems del menú de registro y control.
 * Encapsula la lógica para recuperar los datos del menú desde un repositorio local.
 *
 * @property localRepository El repositorio para acceder a los datos locales del menú.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetControlRegisterUseCase(
    private val localRepository: MenuLocalRepository,
) {
    /**
     * Ejecuta el proceso para obtener la lista de ítems del menú de registro y control.
     *
     * @return Un [ResultModel] que contiene la lista de ítems del menú o un estado de error
     * si la lista está vacía o si ocurre una excepción.
     */
    operator fun invoke(): ResultModel<List<ModelPrincipalMenuData>, String> {
        return runCatching { localRepository.getControlRegister() }.fold(
            onSuccess = { list ->
                if (list.isEmpty()) ErrorResult(ModelCodeError.ERROR_EMPTY)
                else SuccessResult(list)
            },
            onFailure = { ErrorResult(ModelCodeError.ERROR_CATCH) }
        )
    }
}