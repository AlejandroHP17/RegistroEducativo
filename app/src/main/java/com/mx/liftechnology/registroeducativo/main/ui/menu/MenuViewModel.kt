package com.mx.liftechnology.registroeducativo.main.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DialogGroupPartialDomain
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.domain.usecase.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdatePartialMenuUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.menu.MenuUiData
import com.mx.liftechnology.registroeducativo.main.model.menu.MenuUiDialog
import com.mx.liftechnology.registroeducativo.main.model.menu.MenuUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla principal de menú.
 * 
 * Gestiona el estado de la UI, la obtención de grupos, parciales y opciones del menú.
 * Permite actualizar grupos y parciales seleccionados.
 *
 * @property dispatcherProvider El proveedor de dispatchers para controlar los hilos de ejecución.
 * @property getGroupMenuUseCase El caso de uso para obtener los grupos del menú.
 * @property updateGroupMenuUseCase El caso de uso para actualizar un grupo del menú.
 * @property getControlMenuUseCase El caso de uso para obtener las opciones de control del menú.
 * @property getControlRegisterUseCase El caso de uso para obtener las opciones de registro del menú.
 * @property getListPartialMenuUseCase El caso de uso para obtener la lista de parciales del menú.
 * @property savePartialMenuUseCase El caso de uso para guardar un parcial en el menú.
 * @property updatePartialMenuUseCase El caso de uso para actualizar un parcial en el menú.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getGroupMenuUseCase: GetGroupMenuUseCase,
    private val updateGroupMenuUseCase: UpdateGroupMenuUseCase,
    private val getControlMenuUseCase: GetControlMenuUseCase,
    private val getControlRegisterUseCase: GetControlRegisterUseCase,
    private val getListPartialMenuUseCase: GetListPartialMenuUseCase,
    private val savePartialMenuUseCase: SavePartialMenuUseCase,
    private val updatePartialMenuUseCase: UpdatePartialMenuUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())
    /** El estado de la UI para la pantalla. */
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    private val _dialogState = MutableStateFlow(MenuUiDialog())
    /** El estado para los diálogos. */
    val dialogState: StateFlow<MenuUiDialog> = _dialogState.asStateFlow()

    private val _dataState = MutableStateFlow(MenuUiData())
    /** El estado de los datos para la pantalla. */
    val dataState: StateFlow<MenuUiData> = _dataState.asStateFlow()

    /**
     * Obtiene todas las opciones para el ciclo escolar (grupos, parciales, etc.).
     */
    fun getGroup() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = EnumUi.LOADING) }
            
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getGroupMenuUseCase.invoke()
            }

            when (result) {
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
                        it.copy(uiState = EnumUi.NOTHING)
                    }
                }

                is ErrorResult -> {
                    val userError = ErrorMapper.mapErrorToUI(result.error)
                    val messageRes = ErrorToMessageMapper.mapErrorToMessage(
                        error = userError,
                        context = ErrorToMessageMapper.ErrorContext.GENERIC
                    )

                    _uiState.update {
                        it.copy(
                            uiState = EnumUi.ERROR,
                            controlToast = messageRes?.let { msg ->
                                ToastUiState(
                                    messageToast = msg,
                                    showToast = true,
                                    typeToast = TypeToastUi.ERROR
                                )
                            } ?: it.controlToast.copy(showToast = false)
                        )
                    }
                }
            }
        }
    }

    /**
     * Actualiza el grupo seleccionado.
     *
     * @param nameItem El grupo seleccionado.
     */
    fun updateGroup(nameItem: DialogStudentGroupDomain) {
        viewModelScope.launch {
            // Actualizaciones de estado simples no necesitan dispatcher específico
            _dialogState.update { it.copy(studentGroupItem = nameItem) }
            
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            withContext(dispatcherProvider.io) {
                updateGroupMenuUseCase.invoke(nameItem)
            }
            getListPartialCompose()
        }
    }

    private suspend fun getListPartialCompose() {
        // Las operaciones de red deben ejecutarse en el dispatcher de I/O
        val result = withContext(dispatcherProvider.io) {
            getListPartialMenuUseCase.invoke()
        }

        when (result) {
            is SuccessResult -> {
                val itemSelected = withContext(dispatcherProvider.io) {
                    savePartialMenuUseCase.invoke(result.data)
                }
                
                val studentGroupItem = _dialogState.value.studentGroupItem.copy(
                    listItemPartial = result.data,
                    namePartial = itemSelected?.name,
                    itemPartial = itemSelected
                )

                val studentGroupList = _dialogState.value.studentGroupList.map { groupItem ->
                    if (groupItem.itemPartial?.partialId == itemSelected?.partialId) {
                        groupItem.copy(
                            listItemPartial = result.data,
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
            is ErrorResult -> {
                _uiState.update { it.copy(uiState = EnumUi.ERROR) }
            }
        }
    }

    /**
     * Obtiene los ítems de control del ciclo escolar.
     */
    fun getControlMenu() {
        viewModelScope.launch {
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            val result = withContext(dispatcherProvider.io) {
                getControlMenuUseCase.invoke()
            }

            when (result) {
                is SuccessResult -> {
                    _uiState.update {
                        it.copy(uiState = EnumUi.NOTHING)
                    }
                    _dataState.update {
                        it.copy(controlItems = result.data)
                    }
                }

                is ErrorResult -> {
                    _uiState.update {
                        it.copy(uiState = EnumUi.ERROR)
                    }
                }
            }
        }
    }

    private suspend fun showGetControlRegister() {
        // Las operaciones de red deben ejecutarse en el dispatcher de I/O
        val result = withContext(dispatcherProvider.io) {
            getControlRegisterUseCase.invoke()
        }

        when (result) {
            is SuccessResult -> {
                _uiState.update {
                    it.copy(
                        showControl = true,
                        uiState = EnumUi.NOTHING
                    )
                }
                _dataState.update {
                    it.copy(evaluationItems = result.data)
                }
            }

            is ErrorResult -> {
                _uiState.update {
                    it.copy(
                        uiState = EnumUi.ERROR,
                        showControl = false
                    )
                }
            }
        }
    }

    /**
     * Actualiza el parcial seleccionado.
     *
     * @param partialItem El parcial seleccionado.
     */
    fun updatePartial(partialItem: DialogGroupPartialDomain?) {
        viewModelScope.launch {
            // Actualizaciones de estado simples no necesitan dispatcher específico
            _dialogState.update {
                it.copy(
                    studentGroupItem = it.studentGroupItem.copy(
                        itemPartial = partialItem,
                        namePartial = partialItem?.name
                    )
                )
            }
            
            // Las operaciones de red deben ejecutarse en el dispatcher de I/O
            withContext(dispatcherProvider.io) {
                updatePartialMenuUseCase.invoke(partialItem)
            }
        }
    }

    /**
     * Modifica la visibilidad del mensaje toast.
     *
     * @param show `true` para mostrar el toast, `false` para ocultarlo.
     */
    fun modifyShowToast(show: Boolean) {
        
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(showToast = show)
            )
        }
    }
}