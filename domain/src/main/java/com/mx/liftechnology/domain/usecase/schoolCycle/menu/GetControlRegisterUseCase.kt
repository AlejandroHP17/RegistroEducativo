/**
 * @file Define el caso de uso para obtener la lista de ítems del menú de registro y control.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.domain.usecase.schoolCycle.menu

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.PrincipalMenuDomain
import com.mx.liftechnology.domain.repository.schoolCycle.menu.MenuLocalRepository


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
     * @return Un [ModelResult] que contiene la lista de ítems del menú o un estado de error
     * si la lista está vacía o si ocurre una excepción.
     */
    suspend operator fun invoke(): ModelResult<List<PrincipalMenuDomain>, ModelError> {
        val result = localRepository.getControlRegister()

        return when (result) {
            is SuccessResult -> {
                if (result.data.isEmpty()) ErrorResult(LocalModelError.EMPTY)
                else SuccessResult(result.data)
            }

            is ErrorResult -> {
                ErrorResult(LocalModelError.CATCH)
            }
        }
    }
}