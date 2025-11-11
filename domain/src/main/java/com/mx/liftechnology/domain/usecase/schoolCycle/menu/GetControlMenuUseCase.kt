/**
 * @file Define el caso de uso para obtener la lista de ítems del menú de control.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.menu

import com.mx.liftechnology.data.model.schoolCycle.ModelPrincipalMenuData
import com.mx.liftechnology.data.repository.schoolCycle.menu.MenuLocalRepository
import com.mx.liftechnology.data.util.Error
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.LocalError
import com.mx.liftechnology.data.util.ModelResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.domain.model.generic.ResultModel

/**
 * Caso de uso para obtener la lista de ítems del menú de control.
 * Encapsula la lógica para recuperar los datos del menú desde un repositorio local.
 *
 * @property localRepository El repositorio para acceder a los datos locales del menú.
 * @author Pelkidev
 * @version 1.0.0
 */
class GetControlMenuUseCase(
    private val localRepository: MenuLocalRepository,
) {

    /**
     * Ejecuta el proceso para obtener la lista de ítems del menú de control.
     *
     * @return Un [ResultModel] que contiene la lista de ítems del menú o un estado de error
     * si la lista está vacía o si ocurre una excepción.
     */
    operator fun invoke(): ModelResult<List<ModelPrincipalMenuData>, Error> {
        return runCatching { localRepository.getControlMenu() }.fold(
            onSuccess = { list ->
                if (list.isEmpty()) ErrorResult(LocalError.EMPTY)
                else SuccessResult(list)
            },
            onFailure = { ErrorResult(LocalError.CATCH)}
        )
    }
}