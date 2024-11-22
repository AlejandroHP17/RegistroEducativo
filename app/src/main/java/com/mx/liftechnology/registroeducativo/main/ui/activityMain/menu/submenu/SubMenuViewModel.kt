package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.submenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.domain.usecase.flowmenu.MenuUseCase
import com.mx.liftechnology.domain.usecase.flowmenu.SubMenuUseCase
import com.mx.liftechnology.registroeducativo.framework.CoroutineScopeManager
import kotlinx.coroutines.launch

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
 * @param useCase Access to UseCase with DI
 */
class SubMenuViewModel(
    private val useCase: SubMenuUseCase
) : ViewModel() {

    // Controlled coroutine
    private val coroutine = CoroutineScopeManager()

    // List the option from menu
    private val _nameSubMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>>>()
    val nameSubMenu: LiveData<ModelState<List<ModelAdapterMenu>>> = _nameSubMenu

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getSubMenu(school:Boolean) {
        coroutine.scopeIO.launch {
            runCatching {
                useCase.getSubMenu(school)
            }.onSuccess {
                _nameSubMenu.postValue(it)
            }.onFailure {
                _nameSubMenu.postValue(ErrorState(ModelCodeError.ERROR_FUNCTION))
            }
        }
    }
}