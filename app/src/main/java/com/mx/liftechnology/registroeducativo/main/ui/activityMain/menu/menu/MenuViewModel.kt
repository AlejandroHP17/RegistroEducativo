package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.domain.model.menu.ModelInfoMenu
import com.mx.liftechnology.domain.usecase.flowmenu.MenuUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.launch

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
 * @param menuUseCase Access to UseCase with DI
 */
class MenuViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val menuUseCase: MenuUseCase
) : ViewModel() {

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // List the option from menu
    private val _nameMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>, String>>()
    val nameMenu: LiveData<ModelState<List<ModelAdapterMenu>, String>> = _nameMenu

    // List the option from menu
    private val _listGroup = MutableLiveData<ModelState<ModelInfoMenu, String>>()
    val listGroup: LiveData<ModelState<ModelInfoMenu, String>> = _listGroup

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getMenu(schoolYear: Boolean) {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuUseCase.getMenu(schoolYear)
            }.onSuccess {
                _nameMenu.postValue(it)
            }.onFailure {
                _nameMenu.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getGroup() {
        _animateLoader.postValue(LoaderState(true))
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuUseCase.getGroup()
            }.onSuccess {
                _listGroup.postValue(it)
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _listGroup.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }
}