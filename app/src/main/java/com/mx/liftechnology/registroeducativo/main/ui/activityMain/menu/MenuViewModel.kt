package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.model.ModelAdapterMenu
import com.mx.liftechnology.domain.model.ModelDialogStudentGroup
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
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
    private val _controlMenu = MutableLiveData<ModelState<List<ModelAdapterMenu>, String>>()
    val controlMenu: LiveData<ModelState<List<ModelAdapterMenu>, String>> = _controlMenu

    // List the option from menu
    private val _controlMenuRegister = MutableLiveData<ModelState<List<ModelAdapterMenu>, String>>()
    val controlMenuRegister: LiveData<ModelState<List<ModelAdapterMenu>, String>> = _controlMenuRegister

    // List the option from menu
    private val _controlMenuEvaluation = MutableLiveData<ModelState<List<ModelAdapterMenu>, String>>()
    val controlMenuEvaluation: LiveData<ModelState<List<ModelAdapterMenu>, String>> = _controlMenuEvaluation

    // List the option from menu
    private val _listGroup = MutableLiveData<ModelState<List<ModelDialogStudentGroup>, String>>()
    val listGroup: LiveData<ModelState<List<ModelDialogStudentGroup>, String>> = _listGroup

    private val _selectedGroup = MutableLiveData<ModelState<ModelDialogStudentGroup, String>>()
    val selectedGroup: LiveData<ModelState<ModelDialogStudentGroup, String>> = _selectedGroup

    /** getGroup - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getGroup() {
        _animateLoader.postValue(LoaderState(true))
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuUseCase.getGroup()
            }.onSuccess {
                // Falta data
                if (it is SuccessState) {
                    showGetControlRegister()
                    showGetEvaluationRegister()
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
    fun getControlMenu() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuUseCase.getControlMenu()
            }.onSuccess {
                _controlMenu.postValue(it)
            }.onFailure {
                _controlMenu.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
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

    private fun showGetControlRegister(){
        viewModelScope.launch (dispatcherProvider.io){
            runCatching {
                menuUseCase.getControlRegister()
            }.onSuccess {
                _controlMenuRegister.postValue(it)
            }.onFailure {
                _controlMenuRegister.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    private fun showGetEvaluationRegister(){
        viewModelScope.launch (dispatcherProvider.io){
            runCatching {
                menuUseCase.getControlEvaluation()
            }.onSuccess {
                _controlMenuEvaluation.postValue(it)
            }.onFailure {
                _controlMenuEvaluation.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }
}