package com.mx.liftechnology.domain.usecase.flowmenu

import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.model.modelBase.EmptyState
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.mainFlow.MenuLocalRepository
import com.mx.liftechnology.data.repository.mainFlow.MenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MenuUseCase {
    suspend fun getMenu(schoolYear:Boolean):ModelState<List<ModelAdapterMenu>,String>
    suspend fun getGroup()
}

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class MenuUseCaseImp(
    private val localRepository: MenuLocalRepository,
    private val menuRepository: MenuRepository,
    private val preference: PreferenceUseCase
) : MenuUseCase{

    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    override suspend fun getMenu(schoolYear:Boolean): ModelState<List<ModelAdapterMenu>,String> {
        return withContext(dispatcher) {
            try {
                val list = localRepository.getItems(schoolYear)
                if (list.isNullOrEmpty()) {
                    EmptyState(ModelCodeError.ERROR_EMPTY)
                } else {
                    SuccessState(list)
                }
            } catch (e: Exception) {
                ErrorState(ModelCodeError.ERROR_CATCH)
            }
        }
    }

    override suspend fun getGroup() {
        val userId= preference.getPreferenceInt(ModelPreference.ID_USER)
        val roleId= preference.getPreferenceInt(ModelPreference.ID_ROLE)

        menuRepository.executeGetGroup(userId, roleId)

    }
}