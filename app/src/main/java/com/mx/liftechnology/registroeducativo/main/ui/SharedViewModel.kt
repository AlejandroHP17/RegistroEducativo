package com.mx.liftechnology.registroeducativo.main.ui

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.registroeducativo.main.model.ModelShareUIState
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(ModelShareUIState())
    val uiState: StateFlow<ModelShareUIState> = _uiState.asStateFlow()

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