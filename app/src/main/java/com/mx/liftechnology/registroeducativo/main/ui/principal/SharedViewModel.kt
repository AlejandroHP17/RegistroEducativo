package com.mx.liftechnology.registroeducativo.main.ui.principal

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.registroeducativo.main.model.ModelShareUIState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateTypeToastUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel compartido para gestionar el estado común de la UI entre diferentes pantallas.
 * Centraliza la gestión de toasts globales que se muestran por encima de toda la navegación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SharedViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(ModelShareUIState())
    /** El estado de la UI para componentes compartidos. */
    val uiState: StateFlow<ModelShareUIState> = _uiState.asStateFlow()

    /**
     * Modifica el estado del toast.
     *
     * @param controlToast El nuevo estado del toast.
     */
    fun modifyShowToast(controlToast: ModelStateToastUI) {
        _uiState.update {
            it.copy(
                controlToast = ModelStateToastUI(
                    messageToast = controlToast.messageToast,
                    showToast = controlToast.showToast,
                    typeToast = controlToast.typeToast
                )
            )
        }
    }

    /**
     * Muestra un toast con el mensaje y tipo especificados.
     * Método helper centralizado para mostrar toasts desde cualquier lugar.
     *
     * @param messageToast El recurso del string del mensaje a mostrar.
     * @param typeToast El tipo de toast (SUCCESS, ERROR, WARNING, INFORMATIVE).
     */
    fun showToast(
        messageToast: Int,
        typeToast: ModelStateTypeToastUI = ModelStateTypeToastUI.SUCCESS
    ) {
        _uiState.update {
            it.copy(
                controlToast = ModelStateToastUI(
                    messageToast = messageToast,
                    showToast = true,
                    typeToast = typeToast
                )
            )
        }
    }

    /**
     * Oculta el toast actual.
     */
    fun hideToast() {
        _uiState.update {
            it.copy(
                controlToast = it.controlToast.copy(
                    showToast = false
                )
            )
        }
    }
}