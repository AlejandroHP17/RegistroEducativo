package com.mx.liftechnology.registroeducativo.main.ui.activityMain.register.school

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.liftechnology.core.network.callapi.ResponseCctSchool
import com.mx.liftechnology.domain.model.generic.ErrorState
import com.mx.liftechnology.domain.model.generic.LoaderState
import com.mx.liftechnology.domain.model.generic.ModelCodeError
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelState
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.SuccessState
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.CCTUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.RegisterOneSchoolUseCase
import com.mx.liftechnology.domain.usecase.mainflowdomain.school.ValidateFieldsRegisterUseCase
import com.mx.liftechnology.registroeducativo.framework.SingleLiveEvent
import com.mx.liftechnology.registroeducativo.main.model.viewmodels.main.ModelRegisterSchoolUIState
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterSchoolViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val cctUseCase: CCTUseCase,
    private val validateFieldsUseCase: ValidateFieldsRegisterUseCase,
    private val registerOneSchoolUseCase: RegisterOneSchoolUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ModelRegisterSchoolUIState())
    val uiState: StateFlow<ModelRegisterSchoolUIState> = _uiState.asStateFlow()

    fun onCycleChanged(cycle: String) {
        _uiState.update { it.copy(
            cycle = cycle,
            isErrorCycle = ModelStateOutFieldText(false, "")
        ) }
    }

    fun onGradeChanged(grade: String) {
        _uiState.update { it.copy(
            grade = grade,
            isErrorGrade = ModelStateOutFieldText(false, "")
        ) }
    }
    fun onGroupChanged(group: String) {
        _uiState.update { it.copy(
            group = group,
            isErrorGroup = ModelStateOutFieldText(false, "")
        ) }
    }

    fun onCctChanged(cct: String) {
        _uiState.update { it.copy(
            cct = cct.uppercase(),
            isErrorCct = ModelStateOutFieldText(false, "")
        ) }
        if(cct.length == 10) {
            _uiState.update { it.copy(
                isLoading = true
            ) }
            getSchoolCCTCompose()
        }else{
            _uiState.update { it.copy(
                schoolName = "",
                shift =  "",
                type =  "",
                isErrorCct = ModelStateOutFieldText(
                    isError = true,
                    errorMessage = ModelCodeInputs.ET_NOT_FOUND),
                read = true)
            }
        }
    }

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    private fun getSchoolCCTCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            _animateLoader.postValue(LoaderState(true))
            runCatching {
                cctUseCase.getSchoolCCTCompose(_uiState.value.cct)
            }.onSuccess { state ->
                if (state is SuccessState) {
                    _uiState.update { it.copy(
                        isLoading = false,
                        schoolName = state.result?.result?.schoolName ?: "",
                        schoolCycleTypeId = state.result?.result?.schoolCycleTypeId ?: -1,
                        shift = state.result?.result?.shift  ?: "",
                        type = state.result?.result?.schoolCycleType ?: "",
                        spinner = state.result?.spinners,
                        read = false
                    ) }
                }else{
                    _uiState.update { it.copy(
                        isLoading = false,
                        schoolName = "",
                        schoolCycleTypeId = -1,
                        shift =  "",
                        type =  "",
                        isErrorCct = ModelStateOutFieldText(
                            isError = true,
                            errorMessage = ModelCodeInputs.ET_NOT_FOUND),
                        read = true)
                    }
                }

            }.onFailure {
                _uiState.update { it.copy(
                    isLoading = false
                ) }
            }
        }
    }

    fun validateFieldsCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            _uiState.update { it.copy(isLoading = true) }
            val gradeState = validateFieldsUseCase.validateGradeCompose(grade)
            val groupState = validateFieldsUseCase.validateGroupCompose(group)
            val cycleState = validateFieldsUseCase.validateCycleCompose(cycle)

            _uiState.update {
                it.copy(
                    isErrorGrade = gradeState,
                    isErrorGroup = groupState,
                    isErrorCycle = cycleState)
            }

            if (!(_uiState.value.isErrorCct.isError && gradeState.isError
                && groupState.isError && cycleState.isError
            ) ){
                registerOneSchoolCompose()
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun registerOneSchoolCompose() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneSchoolUseCase.registerOneSchoolCompose(
                    cct = _uiState.value.cct,
                    schoolCycleTypeId = _uiState.value.schoolCycleTypeId,
                    grade = _uiState.value.grade.toInt(),
                    group = _uiState.value.group,
                    cycle = _uiState.value.cycle.toInt())
            }.onSuccess {
                _uiState.update { it.copy(
                    isLoading = false,
                    isSuccess = true
                ) }
            }.onFailure {
                _uiState.update { it.copy(
                    isLoading = false,
                    isSuccess = false
                ) }
            }
        }
    }

    // Observer the animate loader
    private val _animateLoader = SingleLiveEvent<ModelState<Boolean, Int>>()
    val animateLoader: LiveData<ModelState<Boolean, Int>> get() = _animateLoader

    private val _schoolCctField = SingleLiveEvent<ModelState<ResponseCctSchool?, String>>()
    val schoolCctField: LiveData<ModelState<ResponseCctSchool?, String>> get() = _schoolCctField

    private val _allField = SingleLiveEvent<Boolean>()
    val allField: LiveData<Boolean> get() = _allField

    private val _responseRegisterSchool = SingleLiveEvent<ModelState<List<String?>?, String>>()
    val responseRegisterSchool: LiveData<ModelState<List<String?>?, String>> get() = _responseRegisterSchool

    // Observer the cct field
    private val _cct = SingleLiveEvent<String?>()
    val cct: LiveData<String?> get() = _cct

    private var grade: Int? = null
    private var group: String? = null
    private var cycle: Int? = null

    /** Go to validate  the cct
     * @author pelkidev
     * @since 1.0.0
     * */
    fun getSchoolCCT(cct: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _animateLoader.postValue(LoaderState(true))
            runCatching {
                cctUseCase.getSchoolCCT(cct)
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _schoolCctField.postValue(it)
            }.onFailure {
                _schoolCctField.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    /** Save in viewModel the variable of grade
     * @author pelkidev
     * @since 1.0.0
     * */
    fun saveGrade(data: String) {
        grade = data.replace("°", "").toInt()
    }

    /** Save in viewModel the variable of group
     * @author pelkidev
     * @since 1.0.0
     * */
    fun saveGroup(data: String) {
        group = data
    }

    /** Save in viewModel the variable of group
     * @author pelkidev
     * @since 1.0.0
     * */
    fun saveCycle(data: String?) {
        cycle = data?.toInt() ?: 0
    }

    fun validateFields() {
        viewModelScope.launch(dispatcherProvider.io) {
            _animateLoader.postValue(LoaderState(true))
            val gradeState = validateFieldsUseCase.validateGrade(grade)
            val groupState = validateFieldsUseCase.validateGroup(group)
            val cycleState = validateFieldsUseCase.validateCycle(cycle)

            if (schoolCctField.value is SuccessState && gradeState is SuccessState
                && groupState is SuccessState && cycleState is SuccessState
            ) {
                registerOneSchool()
                _allField.postValue(true)
            } else {
                _allField.postValue(false)
                _animateLoader.postValue(LoaderState(false))
            }
        }
    }

    private fun registerOneSchool() {
        viewModelScope.launch(dispatcherProvider.io) {
            runCatching {
                registerOneSchoolUseCase.registerOneSchool(
                    (schoolCctField.value as SuccessState<ResponseCctSchool?, String>).result,
                    grade,
                    group,
                    cycle
                )
            }.onSuccess {
                _animateLoader.postValue(LoaderState(false))
                _responseRegisterSchool.postValue(it)
            }.onFailure {
                _animateLoader.postValue(LoaderState(false))
                _responseRegisterSchool.postValue(ErrorState(ModelCodeError.ERROR_UNKNOWN))
            }
        }
    }

    fun validateData(state: List<String>) {
        val result = state.firstOrNull()?.replace(" ", "")?.uppercase()
        _cct.postValue(result)
    }
}