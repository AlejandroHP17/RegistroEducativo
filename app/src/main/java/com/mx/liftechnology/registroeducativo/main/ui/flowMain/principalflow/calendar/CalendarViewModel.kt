package com.mx.liftechnology.registroeducativo.main.ui.flowMain.principalflow.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.util.logs
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.model.student.ModelStudentDomain
import com.mx.liftechnology.domain.model.subject.ModelFormatSubjectDomain
import com.mx.liftechnology.domain.usecase.mainflowdomain.student.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.subject.GetListSubjectUseCase
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListStudentDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelListSubjectDataState
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.ModelMenuStateUI
import com.mx.liftechnology.registroeducativo.main.model.viewmodel.main.share.ModelCustomCard
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** CalendarViewModel -
 * @author pelkidev
 * @since 1.0.0
 */
class CalendarViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getListSubjectUseCase: GetListSubjectUseCase,
    private val getListStudentUseCase: GetListStudentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelMenuStateUI())
    val uiState: StateFlow<ModelMenuStateUI> = _uiState.asStateFlow()

    private val _dataState = MutableStateFlow(ModelListSubjectDataState())
    val dataState: StateFlow<ModelListSubjectDataState> = _dataState.asStateFlow()

    private val _dataState2 = MutableStateFlow(ModelListStudentDataState())
    val dataState2: StateFlow<ModelListStudentDataState> = _dataState2.asStateFlow()


    fun getSubject() {
        viewModelScope.launch (dispatcherProvider.main){
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListSubjectUseCase.invoke()){
                is SuccessState -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                    _dataState.update { it.copy(
                        subjectList = result.result,
                        subjectListUI = result.result.convertModelCustomCard(),
                    ) }
                }
                else -> {
                    logs(result.toString())
                    _uiState.update {
                        it.copy(
                            uiState = ModelStateUIEnum.ERROR,
                        )
                    }
                }
            }
        }
    }

    private fun List<ModelFormatSubjectDomain>?.convertModelCustomCard():List<ModelCustomCard>{
        return this?.map {
            ModelCustomCard(
                id = it.subjectId.toString(),
                numberList = "",
                nameCard = "${it.name}"
            )
        }?: emptyList()
    }

    fun getListStudent() {
        viewModelScope.launch {
            _uiState.update { it.copy(uiState = ModelStateUIEnum.LOADING) }

            when(val result = getListStudentUseCase.invoke()){
                is SuccessState -> {
                    _uiState.update {
                        it.copy(uiState = ModelStateUIEnum.NOTHING)
                    }
                    _dataState2.update {
                        it.copy(
                            studentList = result.result,
                            studentListUI = result.result.convertModelCustomCard2()
                        )
                    }
                }
                else -> {
                    _uiState.update { it.copy(uiState = ModelStateUIEnum.NOTHING) }
                }
            }
        }
    }

    private fun List<ModelStudentDomain>?.convertModelCustomCard2(): List<ModelCustomCard> {
        return this?.sortedWith(
            compareBy(
                { it.lastName ?: "" },
                { it.secondLastName ?: "" },
                { it.name ?: "" }
            ))
            ?.mapIndexed { index, student ->
                ModelCustomCard(
                    id = student.studentId ?: "",
                    numberList = (index + 1).toString(), // Numeración comenzando en 1
                    nameCard = "${student.lastName} ${student.secondLastName} ${student.name}".trim()
                )
            } ?: emptyList()
    }
}