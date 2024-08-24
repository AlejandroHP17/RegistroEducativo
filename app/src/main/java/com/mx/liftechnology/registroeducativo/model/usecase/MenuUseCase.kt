package com.mx.liftechnology.registroeducativo.model.usecase

import com.mx.liftechnology.registroeducativo.data.local.repository.MenuRepository
import com.mx.liftechnology.registroeducativo.model.util.EmptyState
import com.mx.liftechnology.registroeducativo.model.util.ErrorState
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.model.util.ModelCodeError
import com.mx.liftechnology.registroeducativo.model.util.ModelState
import com.mx.liftechnology.registroeducativo.model.util.SuccessState
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