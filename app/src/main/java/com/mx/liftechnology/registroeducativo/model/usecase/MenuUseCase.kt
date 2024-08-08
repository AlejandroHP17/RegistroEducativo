package com.mx.liftechnology.registroeducativo.model.usecase

import com.mx.liftechnology.registroeducativo.data.local.repository.MenuRepository
import com.mx.liftechnology.registroeducativo.model.dataclass.EmptyState
import com.mx.liftechnology.registroeducativo.model.dataclass.ErrorState
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelCodeError
import com.mx.liftechnology.registroeducativo.model.dataclass.ModelState
import com.mx.liftechnology.registroeducativo.model.dataclass.SuccessState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MenuUseCase (private val localRepository: MenuRepository, ){

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
    /** Obtiene el listado de categorias y procesa la informacion para el viewmodel
     * @author pelkidev
     * @date 28/08/2023
     * */
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