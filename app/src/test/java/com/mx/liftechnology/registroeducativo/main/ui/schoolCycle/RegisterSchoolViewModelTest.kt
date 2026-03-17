package com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.school

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.core.util.voice.VoiceRecognitionManager
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.school.GetCctUseCase
import com.mx.liftechnology.domain.usecase.school.RegisterSchoolWithValidationUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import com.mx.liftechnology.registroeducativo.main.util.navigation.AppRoutes
import com.mx.liftechnology.registroeducativo.main.util.toSelectPeriod
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterSchoolViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterSchoolViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getCctUseCase: GetCctUseCase
    private lateinit var registerSchoolWithValidationUseCase: RegisterSchoolWithValidationUseCase
    private lateinit var voiceRecognitionManager: VoiceRecognitionManager
    private lateinit var viewModel: RegisterSchoolViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        getCctUseCase = mockk()
        registerSchoolWithValidationUseCase = mockk()
        voiceRecognitionManager = mockk(relaxed = true) {
            every { resultsStateFlow } returns kotlinx.coroutines.flow.MutableStateFlow<List<String>>(emptyList())
        }
        viewModel = RegisterSchoolViewModel(dispatcherProvider, getCctUseCase, registerSchoolWithValidationUseCase, voiceRecognitionManager)
    }

    @Test
    fun `onTypeChanged actualiza type y cycle`() = runTest(testDispatcher) {
        val spinnerItems = listOf(ModelCustomSpinner(id = 1, value = "2024-2025"))
        val periodCatalog = com.mx.liftechnology.domain.model.schoolCycle.PeriodCatalogDomain(
            type = ModelCustomSpinner(1, "ANUAL"),
            cycle = spinnerItems.toSelectPeriod(),
            shift = null
        )
        val uiSemi = viewModel.uiSemiAutomaticData.first().copy(periodCatalog = periodCatalog, spinner = com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiSemiAutomaticData().spinner?.copy(cycle = spinnerItems.toSelectPeriod()))
        // Forzamos el estado semi-automático
        val stateField = RegisterSchoolViewModel::class.java.getDeclaredField("_uiSemiAutomaticData")
        stateField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val flow = stateField.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<com.mx.liftechnology.registroeducativo.main.model.schoolCycle.RegisterSchoolUiSemiAutomaticData>
        flow.value = uiSemi

        val typeSpinner = ModelCustomSpinner(1, "ANUAL")

        viewModel.onTypeChanged(typeSpinner)

        val inputState = viewModel.inputState.first()
        assertEquals("ANUAL", inputState.type.valueText)
    }

    @Test
    fun `onCctChanged con longitud 10 inicia carga`() = runTest(testDispatcher) {
        coEvery { getCctUseCase.invoke(any()) } returns SuccessResult(mockk(relaxed = true))

        val cct = ModelStateOutFieldText(valueText = "TEST123456")
        viewModel.onCctChanged(cct)

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.NOTHING, uiState.uiState) // tras llamada exitosa vuelve a NOTHING
    }

    @Test
    fun `onCctChanged con longitud distinta limpia datos y marca error`() = runTest(testDispatcher) {
        val cct = ModelStateOutFieldText(valueText = "SHORT")

        viewModel.onCctChanged(cct)

        val inputState = viewModel.inputState.first()
        val semi = viewModel.uiSemiAutomaticData.first()

        assertEquals(ModelCodeInputs.ET_NOT_FOUND, inputState.cct.errorMessage)
        assertEquals("", semi.schoolName.valueText)
    }

    @Test
    fun `registerSchool exito muestra toast y navega`() = runTest(testDispatcher) {
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = SuccessResult(Any()) as ModelResult<Any, ModelError>
        )

        coEvery { registerSchoolWithValidationUseCase.invoke(any(), any(), any(), any(), any(), any()) } returns validationResult

        viewModel.registerSchool()

        val uiState: RegisterSchoolUiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_register_school, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
    }

    @Test
    fun `registerSchool ErrorResult muestra toast error`() = runTest(testDispatcher) {
        val modelError: ModelError = mockk()
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = ErrorResult(modelError) as ModelResult<Any, ModelError>
        )

        coEvery { registerSchoolWithValidationUseCase.invoke(any(), any(), any(), any(), any(), any()) } returns validationResult

        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.REGISTER_SCHOOL
            )
        } returns R.string.app_name

        viewModel.registerSchool()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)
    }
}
