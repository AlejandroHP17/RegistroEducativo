package com.mx.liftechnology.registroeducativo.main.ui.principal

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.registroeducativo.main.model.ModelShareUIState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A shared ViewModel for managing common UI state across different screens.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SharedViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(ModelShareUIState())
    /** The UI state for shared components. */
    val uiState: StateFlow<ModelShareUIState> = _uiState.asStateFlow()

    /**
     * Modifies the toast message state.
     *
     * @param controlToast The new toast state.
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
}