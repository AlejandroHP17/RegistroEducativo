package com.mx.liftechnology.domain.usecase.flowmenu

import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.model.modelBase.EmptyState
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.repository.mainFlow.SubMenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class SubMenuUseCase(private val localRepository: SubMenuRepository) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend fun getSubMenu(school:Boolean): ModelState<List<ModelAdapterMenu>> {
        return withContext(dispatcher) {
            try {
                val list = localRepository.getItems(school)
                if (list.isNullOrEmpty()) {
                    EmptyState()
                } else {
                    SuccessState(list)
                }
            } catch (e: Exception) {
                ErrorState(ModelCodeError.ERROR_FUNCTION)
            }
        }
    }
}