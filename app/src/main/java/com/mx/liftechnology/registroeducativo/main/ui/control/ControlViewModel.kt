package com.mx.liftechnology.registroeducativo.main.ui.control

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.repository.auth.AuthRepository
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import com.mx.liftechnology.domain.repository.partial.PartialRepository
import com.mx.liftechnology.domain.repository.school.SchoolRepository
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository
import com.mx.liftechnology.domain.repository.student.StudentRepository
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel para la pantalla de control de APIs.
 * Permite consumir servicios y mostrar el JSON de respuesta.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ControlViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val authRepository: AuthRepository,
    private val studentRepository: StudentRepository,
    private val formativeFieldRepository: FormativeFieldRepository,
    private val evaluationRepository: EvaluationRepository,
    private val partialRepository: PartialRepository,
    private val schoolRepository: SchoolRepository,
    private val schoolCycleRepository: SchoolCycleRepository,
    private val workTypeRepository: WorkTypeRepository
) : ViewModel() {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    private val _uiState = MutableStateFlow(ApiControlUiState())
    val uiState: StateFlow<ApiControlUiState> = _uiState.asStateFlow()

    /**
     * Actualiza el texto de parámetros ingresado por el usuario.
     */
    fun updateParameterText(text: String) {
        _uiState.update { it.copy(parameterText = text) }
    }

    /**
     * Limpia la respuesta actual.
     */
    fun clearResponse() {
        _uiState.update { it.copy(responseJson = "", isLoading = false, error = null) }
    }

    /**
     * Consume el servicio de obtener datos de usuario.
     */
    fun callNewCode() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val result = withContext(dispatcherProvider.io) {
                authRepository.getData()
            }
            
            handleResponse(result, "getUserData")
        }
    }


    /**
     * Consume el servicio de obtener datos de usuario.
     */
    fun callGetUserData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }

            val result = withContext(dispatcherProvider.io) {
                authRepository.getData()
            }

            handleResponse(result, "getUserData")
        }
    }

    /**
     * Consume el servicio de obtener lista de estudiantes.
     */
    fun callGetStudents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val cycleSchoolId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                studentRepository.getStudents(cycleSchoolId)
            }
            
            handleResponse(result, "getStudents")
        }
    }

    /**
     * Consume el servicio de obtener lista de campos formativos.
     */
    fun callGetFormativeFields() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val cycleSchoolId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                formativeFieldRepository.getList(cycleSchoolId)
            }
            
            handleResponse(result, "getFormativeFields")
        }
    }

    /**
     * Consume el servicio de obtener lista de parciales.
     */
    fun callGetPartials() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val schoolCycleId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                partialRepository.getList(schoolCycleId)
            }
            
            handleResponse(result, "getPartials")
        }
    }

    /**
     * Consume el servicio de obtener CCT de escuela.
     */
    fun callGetCct() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val cct = _uiState.value.parameterText.ifBlank { "TEST123456" }
            
            val result = withContext(dispatcherProvider.io) {
                schoolRepository.getCct(cct)
            }
            
            handleResponse(result, "getCct")
        }
    }

    /**
     * Consume el servicio de obtener ciclos escolares.
     */
    fun callGetSchoolCycles() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val teacherId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                schoolCycleRepository.getCycleSchool(teacherId)
            }
            
            handleResponse(result, "getSchoolCycles")
        }
    }

    /**
     * Consume el servicio de obtener tipos de trabajo.
     */
    fun callGetWorkTypes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val teacherId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                workTypeRepository.getWorkTypeList(teacherId)
            }
            
            handleResponse(result, "getWorkTypes")
        }
    }

    /**
     * Consume el servicio de obtener tipos de trabajo por campo formativo.
     */
    fun callGetWorkTypesByFormativeField() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val formativeFieldId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                workTypeRepository.getWorkTypeByFormativeField(formativeFieldId)
            }
            
            handleResponse(result, "getWorkTypesByFormativeField")
        }
    }

    /**
     * Consume el servicio de obtener lista de evaluaciones.
     */
    fun callGetEvaluations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            // Parámetros: schoolCycleId,partialId,formativeFieldId,workTypeId,studentId
            val params = _uiState.value.parameterText.split(",")
            val schoolCycleId = params.getOrNull(0)?.toIntOrNull() ?: 1
            val partialId = params.getOrNull(1)?.toIntOrNull() ?: 1
            val formativeFieldId = params.getOrNull(2)?.toIntOrNull() ?: 1
            val workTypeId = params.getOrNull(3)?.toIntOrNull() ?: 1
            val studentId = params.getOrNull(4)?.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                evaluationRepository.getListEvaluations(
                    schoolCycleId = schoolCycleId,
                    partialId = partialId,
                    formativeFieldId = formativeFieldId,
                    workTypeId = workTypeId,
                    studentId = studentId,
                    workDate = null,
                    workDateFrom = null,
                    workDateTo = null
                )
            }
            
            handleResponse(result, "getEvaluations")
        }
    }

    /**
     * Consume el servicio de obtener lista de tipos de trabajo y campos formativos.
     */
    fun callGetWotyFofi() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, responseJson = "") }
            
            val schoolCycleId = _uiState.value.parameterText.toIntOrNull() ?: 1
            
            val result = withContext(dispatcherProvider.io) {
                formativeFieldRepository.getListWotyFofi(schoolCycleId)
            }
            
            handleResponse(result, "getWotyFofi")
        }
    }

    /**
     * Maneja la respuesta del servicio y la convierte a JSON.
     */
    private fun handleResponse(result: Any, serviceName: String) {
        _uiState.update { it.copy(isLoading = false) }
        
        when (result) {
            is SuccessResult<*> -> {
                val json = try {
                    gson.toJson(result.data)
                } catch (e: Exception) {
                    "Error serializando respuesta: ${e.message}\n\nDatos: $result"
                }
                _uiState.update {
                    it.copy(
                        responseJson = "✅ $serviceName - SUCCESS\n\n$json",
                        error = null
                    )
                }
            }
            is ErrorResult<*> -> {
                val json = try {
                    gson.toJson(result.error)
                } catch (e: Exception) {
                    "Error: ${result.error}"
                }
                _uiState.update {
                    it.copy(
                        responseJson = "❌ $serviceName - ERROR\n\n$json",
                        error = result.error.toString()
                    )
                }
            }
            else -> {
                val json = try {
                    gson.toJson(result)
                } catch (e: Exception) {
                    result.toString()
                }
                _uiState.update {
                    it.copy(
                        responseJson = "⚠️ $serviceName - UNKNOWN\n\n$json",
                        error = null
                    )
                }
            }
        }
    }
}

/**
 * Estado de la UI para la pantalla de control de APIs.
 */
data class ApiControlUiState(
    val parameterText: String = "",
    val responseJson: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
