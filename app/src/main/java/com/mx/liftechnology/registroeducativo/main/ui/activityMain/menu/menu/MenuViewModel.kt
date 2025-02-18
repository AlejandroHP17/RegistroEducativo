package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.model.ModelDialogStudentGroup
import com.mx.liftechnology.core.model.modelBase.ErrorState
import com.mx.liftechnology.core.model.modelBase.LoaderState
import com.mx.liftechnology.core.model.modelBase.ModelCodeError
import com.mx.liftechnology.core.model.modelBase.ModelState
import com.mx.liftechnology.core.model.modelBase.SuccessState
import com.mx.liftechnology.data.model.ModelAdapterMenu
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
    private val _listGroup = MutableLiveData<ModelState<List<ModelDialogStudentGroup>, String>>()
    val listGroup: LiveData<ModelState<List<ModelDialogStudentGroup>, String>> = _listGroup

    private val _selectedGroup = MutableLiveData<ModelState<ModelDialogStudentGroup, String>>()
    val selectedGroup: LiveData<ModelState<ModelDialogStudentGroup, String>> = _selectedGroup

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
                getMenu(true)
                // Falta data
                if (it is SuccessState) {
                    _listGroup.postValue(SuccessState(it.result.listSchool))
                    _selectedGroup.postValue(SuccessState(it.result.infoSchoolSelected))
                }
                _animateLoader.postValue(LoaderState(false))
            }.onFailure {
                _selectedGroup.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

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

    fun updateGroup(nameItem: ModelDialogStudentGroup?) {
        viewModelScope.launch(dispatcherProvider.io) {
            nameItem?.let {
                menuUseCase.updateGroup(it)
                _selectedGroup.postValue(SuccessState(it))
            }
        }
    }
}