package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.data.model.ModelPrincipalMenuData
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.MenuGroupsUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.MenuUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
 * @param menuUseCase Access to UseCase with DI
 */
class MenuViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val menuGroupsUseCase: MenuGroupsUseCase,
    private val menuUseCase: MenuUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuUIState())
    val uiState: StateFlow<ModelMenuUIState> = _uiState.asStateFlow()

    fun showDialog(show:Boolean) {
        _uiState.update { it.copy(
            showDialog = show
        ) }
    }

    fun updateGroupCompose(nameItem: ModelDialogStudentGroupDomain) {
        _uiState.update { it.copy(
            studentGroupItem = nameItem
        ) }
        viewModelScope.launch(dispatcherProvider.io) {
            menuGroupsUseCase.updateGroup(nameItem)
        }

    }

    /** getGroup - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getGroupCompose() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuGroupsUseCase.getGroup()
            }.onSuccess {state ->
                if (state is SuccessState) {
                    showGetControlRegisterCompose()
                    _uiState.update { it.copy(
                        studentGroupItem = state.result.infoSchoolSelected,
                        studentGroupList = state.result.listSchool
                    ) }
                }
                _uiState.update { it.copy(isLoading = false) }
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    /** getControlMenu -
     * @author pelkidev
     * @since 1.0.0
     */
    fun getControlMenuCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuUseCase.getControlMenu()
            }.onSuccess { state ->
                if(state is SuccessState){
                    _uiState.update { it.copy(
                        controlItems = state.result
                    ) }
                }
            }
        }
    }

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    // List the option from menu
    private val _controlMenu = MutableLiveData<ModelState<List<ModelPrincipalMenuData>, String>>()
    val controlMenu: LiveData<ModelState<List<ModelPrincipalMenuData>, String>> = _controlMenu

    // List the option from menu
    private val _controlMenuRegister = MutableLiveData<ModelState<List<ModelPrincipalMenuData>, String>>()
    val controlMenuRegister: LiveData<ModelState<List<ModelPrincipalMenuData>, String>> = _controlMenuRegister

    // List the option from menu
    private val _listGroup = MutableLiveData<ModelState<List<ModelDialogStudentGroupDomain>, String>>()
    val listGroup: LiveData<ModelState<List<ModelDialogStudentGroupDomain>, String>> = _listGroup

    private val _selectedGroup = MutableLiveData<ModelState<ModelDialogStudentGroupDomain, String>>()
    val selectedGroup: LiveData<ModelState<ModelDialogStudentGroupDomain, String>> = _selectedGroup

    /** getGroup - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getGroup() {
        _animateLoader.postValue(LoaderState(true))
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuGroupsUseCase.getGroup()
            }.onSuccess {
                if (it is SuccessState) {
                    showGetControlRegister()
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

    /** getControlMenu -
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

    fun updateGroup(nameItem: ModelDialogStudentGroupDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            nameItem?.let {
                menuGroupsUseCase.updateGroup(it)
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

    private fun showGetControlRegisterCompose(){
        viewModelScope.launch (dispatcherProvider.io){
            runCatching {
                menuUseCase.getControlRegister()
            }.onSuccess { state ->
                if(state is SuccessState){
                    _uiState.update { it.copy(
                        evaluationItems = state.result,
                        showControl = true
                    ) }
                }else{
                    _uiState.update { it.copy(showControl = false) }
                }
            }.onFailure {
                _uiState.update { it.copy(showControl = false) }
            }
        }
    }


}