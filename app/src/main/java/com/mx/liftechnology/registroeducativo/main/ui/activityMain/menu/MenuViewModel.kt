package com.mx.liftechnology.registroeducativo.main.ui.activityMain.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.model.generic.ErrorUnauthorizedState
import com.mx.liftechnology.domain.model.generic.ErrorUserState
import com.mx.liftechnology.domain.model.generic.SuccessState
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
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIData
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIDialog
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** MenuViewModel - Control the data of the menu
 * @author pelkidev
 * @since 1.0.0
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

    private val _uiState = MutableStateFlow(ModelMenuUIState())
    val uiState: StateFlow<ModelMenuUIState> = _uiState.asStateFlow()

    private val _uiDialog = MutableStateFlow(ModelMenuUIDialog())
    val uiDialog: StateFlow<ModelMenuUIDialog> = _uiDialog.asStateFlow()

    private val _uiData = MutableStateFlow(ModelMenuUIData())
    val uiData: StateFlow<ModelMenuUIData> = _uiData.asStateFlow()

    /** getGroup - Get all the options from menu, or a mistake in case
     * @author pelkidev
     * @since 1.0.0
     */
    fun getGroup() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }
            when (val state = getGroupMenuUseCase.invoke()) {
                is SuccessState -> {
                    getListPartialCompose()
                    showGetControlRegister()
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                    _uiDialog.update {
                        it.copy(
                            studentGroupItem = state.result.infoSchoolSelected,
                            studentGroupList = state.result.listSchool,
                        )
                    }
                }

                is ErrorUnauthorizedState -> {
                    preference.cleanPreference()
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.UNAUTHORIZED,
                            controlToast = ModelStateToastUI(
                                messageToast = R.string.toast_warning_close_session,
                                showToast = true,
                                typeToast = ModelStateTypeToastUI.WARNING
                            )
                        )
                    }
                }

                else -> {
                    logs(state.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }

    /** Copy the item selected as principal and go to the partial information
     * @author Alejandro Hernandez Pelcastre
     * @since 1.0.0
     */
    fun updateGroup(nameItem: ModelDialogStudentGroupDomain) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiDialog.update { it.copy(studentGroupItem = nameItem) }
            updateGroupMenuUseCase.invoke(nameItem)
            getListPartialCompose()
        }
    }

    private suspend fun getListPartialCompose() {
        when (val state = getListPartialMenuUseCase.invoke()) {
            is SuccessState -> {
                withContext(dispatcherProvider.io) {
                    val itemSelected = savePartialUseCase.invoke(state.result)
                    _uiDialog.update {
                        it.copy(
                            studentGroupItem = it.studentGroupItem.copy(
                                listItemPartial = state.result,
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
                        )
                    }
                }
            }

            is ErrorUserState -> {
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
                    logs(state.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }
            }
        }
    }


    /** getControlMenu -
     * @author pelkidev
     * @since 1.0.0
     */
    fun getControlMenu() {
        viewModelScope.launch(dispatcherProvider.io) {
            when (val state = getControlMenuUseCase.invoke()) {
                is SuccessState -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                    _uiData.update {
                        it.copy(controlItems = state.result)
                    }
                }

                else -> {
                    logs(state.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR
                        )
                    }
                }

            }
        }
    }

    private suspend fun showGetControlRegister() {
        when (val state = getControlRegisterUseCase.invoke()) {
            is SuccessState -> {
                _uiState.update {
                    it.copy(
                        showControl = true,
                        uiState = ModelStateUIEnum.NOTHING
                    )
                }
                _uiData.update {
                    it.copy(evaluationItems = state.result)
                }
            }

            else -> {
                logs(state.toString())
                _uiState.update {
                    it.copy(
                        uiState = ModelStateUIEnum.ERROR,
                        showControl = false
                    )
                }
            }
        }

    }


    fun updatePartial(partialItem: ModelDialogGroupPartialDomain?) {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiDialog.update {
                it.copy(
                    studentGroupItem = it.studentGroupItem.copy(
                        itemPartial = partialItem,
                        namePartial = partialItem?.name
                    )
                )
            }
            updatePartialUseCase.invoke(partialItem?.partialId ?: -1)
        }
    }

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