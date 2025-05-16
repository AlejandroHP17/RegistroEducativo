package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.data.repository.mainflowdata.MenuLocalRepository
import com.mx.liftechnology.domain.model.generic.EmptyState
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


interface MenuUseCase {
    suspend fun getControlMenu(): ModelState<List<ModelPrincipalMenuData>, String>
    suspend fun getControlRegister(): ModelState<List<ModelPrincipalMenuData>, String>
}

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class MenuUseCaseImp(
    private val localRepository: MenuLocalRepository
) : MenuUseCase {

    override suspend fun getControlMenu(): ModelState<List<ModelPrincipalMenuData>, String> {
        return withContext(Dispatchers.IO) {
            try {
                val list = localRepository.getControlMenu()
                if (list.isEmpty()) {
                    EmptyState(ModelCodeError.ERROR_EMPTY)
                } else {
                    SuccessState(list)
                }
            } catch (e: Exception) {
                ErrorState(ModelCodeError.ERROR_CATCH)
            }
        }
    }

    override suspend fun getControlRegister(): ModelState<List<ModelPrincipalMenuData>, String> {
        return withContext(Dispatchers.IO) {
            try {
                val list = localRepository.getControlRegister()
                if (list.isEmpty()) {
                    EmptyState(ModelCodeError.ERROR_EMPTY)
                } else {
                    SuccessState(list)
                }
            } catch (e: Exception) {
                ErrorState(ModelCodeError.ERROR_CATCH)
            }
        }
    }

}



