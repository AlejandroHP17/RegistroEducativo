package com.mx.liftechnology.domain.usecase

import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.util.EmptyState
import com.mx.liftechnology.core.util.ErrorState
import com.mx.liftechnology.core.util.ModelCodeError
import com.mx.liftechnology.core.util.ModelState
import com.mx.liftechnology.core.util.SuccessState
import com.mx.liftechnology.data.repository.MenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class MenuUseCase(private val localRepository: MenuRepository) {

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend fun getMenu(): ModelState<List<ModelAdapterMenu>> {
        return withContext(dispatcher) {
            try {
                val list = localRepository.getItems()
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