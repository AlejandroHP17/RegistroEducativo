package com.mx.liftechnology.domain.usecase.flowmenu

import com.mx.liftechnology.core.model.modelBase.EmptyState
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.data.repository.mainFlow.SubMenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun interface SubMenuUseCase {
    suspend fun getSubMenu(): ModelState<List<ModelAdapterMenu>, String>?
}

/** MenuUseCase - Get the list of menu and process the information
 * @author pelkidev
 * @date 28/08/2023
 * @param localRepository connect with the repository
 * */
class SubMenuUseCaseImp(
    private val localRepository: SubMenuRepository,
    private val preference: PreferenceUseCase
) : SubMenuUseCase {

    override suspend fun getSubMenu(): ModelState<List<ModelAdapterMenu>, String> {
        val activeCycle = preference.getPreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_CYCLE_GROUP)!=-1
        return withContext(Dispatchers.IO) {
            try {
                val list = localRepository.getItems(activeCycle)
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