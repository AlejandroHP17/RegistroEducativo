package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.data.repository.flowMain.menu.MenuLocalRepository
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState


/**
 * Use case for getting the control menu list.
 *
 * @property localRepository The repository for accessing local menu data.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetControlMenuUseCase(
    private val localRepository: MenuLocalRepository,
) {

    /**
     * Executes the process of getting the control menu list.
     *
     * @return A [ModelState] containing the list of menu items or an error.
     */
    operator fun invoke(): ModelState<List<ModelPrincipalMenuData>, String> {
        return runCatching { localRepository.getControlMenu() }.fold(
            onSuccess = { list ->
                if (list.isEmpty()) ErrorState(ModelCodeError.ERROR_EMPTY)
                else SuccessState(list)
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_CATCH) }
        )
    }
}