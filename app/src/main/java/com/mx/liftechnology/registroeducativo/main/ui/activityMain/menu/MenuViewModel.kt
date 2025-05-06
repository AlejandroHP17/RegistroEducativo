package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.MenuGroupsUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.MenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.SavePartialUseCase
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
    private val menuUseCase: MenuUseCase,
    private val getListPartialUseCase: GetListPartialUseCase,
    private val savePartialUseCase: SavePartialUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuUIState())
    val uiState: StateFlow<ModelMenuUIState> = _uiState.asStateFlow()

    fun updateGroup(nameItem: ModelDialogStudentGroupDomain) {
        _uiState.update { it.copy(studentGroupItem = nameItem) }
        viewModelScope.launch(dispatcherProvider.io) {
            menuGroupsUseCase.updateGroup(nameItem)
        }
    }

    /** getGroup - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getGroup() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                menuGroupsUseCase.getGroup()
            }.onSuccess {state ->
                if (state is SuccessState) {
                    getListPartialCompose(state.result.infoSchoolSelected)
                    showGetControlRegister()
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
    fun getControlMenu() {
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

    private fun showGetControlRegister(){
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

    private fun getListPartialCompose(infoSchoolSelected: ModelDialogStudentGroupDomain) {
        viewModelScope.launch (dispatcherProvider.io) {
            runCatching {
                getListPartialUseCase.getListPartial()
            }.onSuccess {state ->
                if(state is SuccessState){
                    val data = savePartialUseCase.savePartial(infoSchoolSelected, state.result)
                    _uiState.update { it.copy(
                        studentGroupItem = it.studentGroupItem.copy(
                            nameItem =  "${it.studentGroupItem.nameItem} - Parcial ${(data?.position ?: 0) + 1}"
                        )
                    ) }
                }
            }.onFailure {
            }
        }
    }


}