package com.mx.liftechnology.registroeducativo.main.ui.activityMain.calendar

import androidx.lifecycle.ViewModel
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelMenuUiState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** CalendarViewModel -
 * @author pelkidev
 * @since 1.0.0
 */
class CalendarViewModel(
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuUiState())
    val uiState: StateFlow<ModelMenuUiState> = _uiState.asStateFlow()


}