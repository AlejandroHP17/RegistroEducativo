package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.MenuGroupsUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.MenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.SavePartialUseCase
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIState
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.control.ModelMenuControlState
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
    private val getListPartialMenuUseCase: GetListPartialMenuUseCase,
    private val savePartialUseCase: SavePartialUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuUIState())
    val uiState: StateFlow<ModelMenuUIState> = _uiState.asStateFlow()

    private val _controlState = MutableStateFlow(ModelMenuControlState())
    val controlState: StateFlow<ModelMenuControlState> = _controlState.asStateFlow()


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
                    _controlState.update { it.copy(
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

    /** Copy the item selected as principal and go to the partial information
     * @author Alejandro Hernandez Pelcastre
     * @since 1.0.0
     */
    fun updateGroup(nameItem: ModelDialogStudentGroupDomain) {
        _controlState.update { it.copy(studentGroupItem = nameItem) }
        viewModelScope.launch(dispatcherProvider.io) {
            menuGroupsUseCase.updateGroup(nameItem)
            getListPartialCompose(_controlState.value.studentGroupItem)
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
                    _controlState.update { it.copy(
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
                        showControl = true
                    ) }
                    _controlState.update { it.copy(
                        evaluationItems = state.result
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
                getListPartialMenuUseCase.getListPartial()
            }.onSuccess {state ->
                if(state is SuccessState){
                    val itemSelected = savePartialUseCase.savePartial(infoSchoolSelected, state.result)

                    _controlState.update { it.copy(
                        studentGroupItem = it.studentGroupItem.copy(
                            listItemPartial =  state.result,
                            namePartial = itemSelected?.name,
                            itemPartial = itemSelected
                        ),
                        studentGroupList = it.studentGroupList.map { groupItem ->
                            if (groupItem.itemPartial?.partialId == itemSelected?.partialId) {
                                groupItem.copy(
                                    listItemPartial = state.result,
                                    namePartial = itemSelected?.name,
                                    itemPartial = itemSelected
                                )
                            } else {
                                groupItem
                            }
                        }
                    ) }
                }else{
                    savePartialUseCase.savePartial(infoSchoolSelected, null)
                }
            }.onFailure {
            }
        }
    }

    fun updatePartial(partialItem: ModelDialogGroupPartialDomain?) {
        _controlState.update {
            it.copy(
                studentGroupItem = it.studentGroupItem.copy(
                    itemPartial = partialItem,
                    namePartial = partialItem?.name
                )
            )
        }

        viewModelScope.launch(dispatcherProvider.io) {
            savePartialUseCase.updatePartial(partialItem?.partialId?:-1)
        }
    }

    fun test(){
        viewModelScope.launch(dispatcherProvider.io) {

        savePartialUseCase.test()
        }
    }

}