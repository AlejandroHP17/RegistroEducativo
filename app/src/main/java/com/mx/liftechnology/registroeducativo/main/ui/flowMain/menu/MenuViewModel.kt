package com.mx.liftechnology.registroeducativo.main.ui.flowMain.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.util.ErrorResult
import com.mx.liftechnology.data.util.SuccessResult
import com.mx.liftechnology.data.util.UserError
import com.mx.liftechnology.domain.model.menu.ModelDialogGroupPartialDomain
import com.mx.liftechnology.domain.model.menu.ModelDialogStudentGroupDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.SavePartialUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.partial.UpdatePartialUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuDataData
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuDialogUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuStateUI
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the main menu screen.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val preference: PreferenceUseCase,
    private val getGroupMenuUseCase: GetGroupMenuUseCase,
    private val updateGroupMenuUseCase: UpdateGroupMenuUseCase,
    private val getControlMenuUseCase: GetControlMenuUseCase,
    private val getControlRegisterUseCase: GetControlRegisterUseCase,
    private val getListPartialMenuUseCase: GetListPartialMenuUseCase,
    private val savePartialUseCase: SavePartialUseCase,
    private val updatePartialUseCase: UpdatePartialUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuStateUI())
    /** The UI state for the screen. */
    val uiState: StateFlow<ModelMenuStateUI> = _uiState.asStateFlow()

    private val _dialogState = MutableStateFlow(ModelMenuDialogUI())
    /** The state for dialogs. */
    val dialogState: StateFlow<ModelMenuDialogUI> = _dialogState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelMenuDataData())
    /** The data state for the screen. */
    val dataState: StateFlow<ModelMenuDataData> = _dataState.asStateFlow()

    /**
     * Gets all the options for the menu.
     */
    fun getGroup() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            when (val result = getGroupMenuUseCase.invoke()) {
                is SuccessResult -> {
                    _dialogState.update {
                        it.copy(
                            studentGroupItem = result.data.infoSchoolSelected,
                            studentGroupList = result.data.listSchool,
                        )
                    }
                    getListPartialCompose()
                    showGetControlRegister()
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }

                }

                is ErrorResult -> {
                    val msg = when(ErrorMapper.mapErrorToUI(result.error)){
                        UserError.UNAUTHORIZED -> R.string.toast_warning_close_session
                        else -> null
                    }

                    if(msg != null){
                        _uiState.update {
                            it.copy(
                                uiState = ModelStateUIEnum.ERROR,
                                controlToast = ModelStateToastUI(
                                    messageToast = msg,
                                    showToast = true,
                                    typeToast = ModelStateTypeToastUI.ERROR
                                )
                            )
                        }
                    }else{
                        _uiState.update { it.copy(uiState = ModelStateUIEnum.ERROR) }
                    }
                }
            }
        }
    }

    /**
     * Updates the selected group.
     *
     * @param nameItem The selected group.
     */
    fun updateGroup(nameItem: ModelDialogStudentGroupDomain) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dialogState.update { it.copy(studentGroupItem = nameItem) }
            updateGroupMenuUseCase.invoke(nameItem)
            getListPartialCompose()
        }
    }

    private suspend fun getListPartialCompose() {
       /* when (val state = getListPartialMenuUseCase.invoke()) {
            is SuccessResult -> {
                withContext(dispatcherProvider.io) {
                    val itemSelected = savePartialUseCase.invoke(state.result)
                    val studentGroupItem =  _dialogState.value.studentGroupItem.copy(
                        listItemPartial = state.result,
                        namePartial = itemSelected?.name,
                        itemPartial = itemSelected
                    )

                    val studentGroupList=  _dialogState.value.studentGroupList.map { groupItem ->
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

                    _dialogState.update {
                        it.copy(
                            studentGroupItem = studentGroupItem,
                            studentGroupList = studentGroupList
                        )
                    }
                }
            }

            is ErrorUserResult -> {
                withContext(dispatcherProvider.io) {
                    savePartialUseCase.invoke(null)
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                            controlToast = ModelStateToastUI(
                                messageToast = R.string.toast_error_update_info_ui,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.ERROR
                            )
                        )
                    }
                }
            }

            else -> {
                withContext(dispatcherProvider.io) {
                    savePartialUseCase.invoke(null)
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }*/
    }

    /**
     * Gets the control menu items.
     */
    fun getControlMenu() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val result = getControlMenuUseCase.invoke()) {
                is SuccessResult -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                    _dataState.update {
                        it.copy(controlItems = result.data)
                    }
                }

                is ErrorResult -> {
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }

            }
        }
    }

    private fun showGetControlRegister() {
        when (val result = getControlRegisterUseCase.invoke()) {
            is SuccessResult -> {
                _uiState.update {
                    it.copy(
                        showControl = true,
                        uiState = ModelStateUIEnum.NOTHING
                    )
                }
                _dataState.update {
                    it.copy(evaluationItems = result.data)
                }
            }

            is ErrorResult -> {
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR,
                        showControl = false
                    )
                }
            }
        }

    }

    /**
     * Updates the selected partial.
     *
     * @param partialItem The selected partial.
     */
    fun updatePartial(partialItem: ModelDialogGroupPartialDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            _dialogState.update {
                it.copy(
                    studentGroupItem = it.studentGroupItem.copy(
                        itemPartial = partialItem,
                        namePartial = partialItem?.name
                    )
                )
            }
            updatePartialUseCase.invoke(partialItem)
        }
    }

    /**
     * Modifies the visibility of the toast message.
     *
     * @param show True to show the toast, false to hide it.
     */
    fun modifyShowToast(show: Boolean) {
        viewModelScope.launch(dispatcherProvider.main) {
            _uiState.update {
                it.copy(
                    controlToast = ModelStateToastUI(
                        messageToast = it.controlToast.messageToast,
                        showToast = show,
                        typeToast = it.controlToast.typeToast
                    )
                )
            }
        }
    }
}