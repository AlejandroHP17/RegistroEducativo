package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.data.repository.mainflowdata.MenuLocalRepository
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState


/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class GetControlMenuUseCase(
    private val localRepository: MenuLocalRepository,
) {

    suspend operator fun invoke(): ModelState<List<ModelPrincipalMenuData>, String> {
        return runCatching { localRepository.getControlMenu() }.fold(
            onSuccess = { list ->
                if (list.isEmpty()) ErrorState(ModelCodeError.ERROR_EMPTY)
                else SuccessState(list)
            },
            onFailure = { ErrorState(ModelCodeError.ERROR_CATCH) }
        )
    }
}