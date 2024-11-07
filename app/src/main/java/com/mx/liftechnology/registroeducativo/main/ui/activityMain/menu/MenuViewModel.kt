package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.domain.usecase.flowmenu.MenuUseCase
import kotlinx.coroutines.launch

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
 * @param useCase Access to UseCase with DI
 */
class MenuViewModel(
    private val useCase: MenuUseCase
) : ViewModel() {

    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // List the option from menu
    private val _nameMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>>>()
    val nameMenu: LiveData<ModelState<List<ModelAdapterMenu>>> = _nameMenu

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getMenu(schoolYear:Boolean) {
        coroutine.scopeIO.launch {
            runCatching {
                useCase.getMenu(schoolYear)
            }.onSuccess {
                _nameMenu.postValue(it)
            }.onFailure {
                _nameMenu.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}