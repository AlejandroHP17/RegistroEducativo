package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.ModelAdapterMenu
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.usecase.flowmenu.MenuUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.Dispatchers
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

    // List the option from menu
    private val _nameMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>,String>>()
    val nameMenu: LiveData<ModelState<List<ModelAdapterMenu>,String>> = _nameMenu

    /** getMenu - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getMenu(schoolYear:Boolean) {
        viewModelScope.launch(dispatcherProvider.io)  {
            runCatching {
                menuUseCase.getMenu(schoolYear)
            }.onSuccess {
                _nameMenu.postValue(it)
            }.onFailure {
                _nameMenu.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }
}