package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.submenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.domain.usecase.flowmenu.SubMenuUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
 * @param useCase Access to UseCase with DI
 */
class SubMenuViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: SubMenuUseCase
) : ViewModel() {

    // List the option from menu
    private val _nameSubMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>, String>?>()
    val nameSubMenu: LiveData<ModelState<List<ModelAdapterMenu>, String>?> = _nameSubMenu

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getSubMenu() {
        viewModelScope.launch(dispatcherProvider.io)  {
            runCatching {
                useCase.getSubMenu()
            }.onSuccess {
                _nameSubMenu.postValue(it)
            }.onFailure {
                _nameSubMenu.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }
}