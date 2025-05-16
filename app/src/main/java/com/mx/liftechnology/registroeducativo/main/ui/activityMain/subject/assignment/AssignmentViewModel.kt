package com.mx.liftechnology.registroeducativo.main.ui.activityMain.subject.assignment

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelAssignmentUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AssignmentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ModelAssignmentUIState())
    val uiState: StateFlow<ModelAssignmentUIState> = _uiState.asStateFlow()

    fun updateSubject(subject: ModelFormatSubjectDomain?){
        _uiState.update { it.copy(
            subject =  subject
        ) }
    }
}