package com.mx.liftechnology.registroeducativo.main.ui.student.register

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.core.util.voice.VoiceRecognitionManager
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.student.EditStudentWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.RegisterStudentWithValidationUseCase
import com.mx.liftechnology.domain.usecase.student.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
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
 * Tests para [RegisterStudentViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterStudentViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var registerStudentWithValidationUseCase: RegisterStudentWithValidationUseCase
    private lateinit var editStudentWithValidationUseCase: EditStudentWithValidationUseCase
    private lateinit var validateVoiceStudentUseCase: ValidateVoiceStudentUseCase
    private lateinit var voiceRecognitionManager: VoiceRecognitionManager
    private lateinit var viewModel: RegisterStudentViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        registerStudentWithValidationUseCase = mockk()
        editStudentWithValidationUseCase = mockk()
        validateVoiceStudentUseCase = mockk()
        voiceRecognitionManager = mockk(relaxed = true) {
            every { resultsStateFlow } returns kotlinx.coroutines.flow.MutableStateFlow<List<String>>(emptyList())
        }
        viewModel = RegisterStudentViewModel(
            dispatcherProvider,
            registerStudentWithValidationUseCase,
            editStudentWithValidationUseCase,
            validateVoiceStudentUseCase,
            voiceRecognitionManager
        )
    }

    @Test
    fun `onNameChanged actualiza input`() = runTest(testDispatcher) {
        val name = ModelStateOutFieldText(valueText = "Juan")

        viewModel.onNameChanged(name)

        val inputs = viewModel.uiInputs.first()
        assertEquals(name, inputs.name)
    }

    @Test
    fun `onCurpChanged actualiza curp`() = runTest(testDispatcher) {
        val curp = ModelStateOutFieldText(valueText = "CURP123456HDFGRN01")

        viewModel.onCurpChanged(curp)

        val inputs = viewModel.uiInputs.first()
        assertEquals(curp, inputs.curp)
    }

    @Test
    fun `validateFieldsCompose registro exito muestra toast success`() = runTest(testDispatcher) {
        viewModel.onNameChanged("Juan".stringToModelStateOutFieldText())
        viewModel.onLastNameChanged("Perez".stringToModelStateOutFieldText())
        viewModel.onSecondLastNameChanged("Lopez".stringToModelStateOutFieldText())
        viewModel.onCurpChanged("CURP123456HDFGRN01".stringToModelStateOutFieldText())
        viewModel.onBirthdayChanged("2000-01-01")
        viewModel.onPhoneNumberChanged("1234567890".stringToModelStateOutFieldText())

        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = SuccessResult(Any()) as ModelResult<Any, ModelError>
        )

        coEvery { registerStudentWithValidationUseCase.invoke(any(), any(), any(), any(), any(), any()) } returns validationResult

        viewModel.validateFieldsCompose()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_register_student, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
    }

    @Test
    fun `validateFieldsCompose registro ErrorResult muestra toast error`() = runTest(testDispatcher) {
        viewModel.onNameChanged("Juan".stringToModelStateOutFieldText())
        viewModel.onLastNameChanged("Perez".stringToModelStateOutFieldText())
        viewModel.onSecondLastNameChanged("Lopez".stringToModelStateOutFieldText())
        viewModel.onCurpChanged("CURP123456HDFGRN01".stringToModelStateOutFieldText())
        viewModel.onBirthdayChanged("2000-01-01")
        viewModel.onPhoneNumberChanged("1234567890".stringToModelStateOutFieldText())

        val modelError: ModelError = mockk()
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = ErrorResult(modelError) as ModelResult<Any, ModelError>
        )

        coEvery { registerStudentWithValidationUseCase.invoke(any(), any(), any(), any(), any(), any()) } returns validationResult

        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.REGISTER_STUDENT
            )
        } returns R.string.app_name

        viewModel.validateFieldsCompose()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)
    }

    @Test
    fun `getArguments pone ViewModel en modo edicion`() = runTest(testDispatcher) {
        val student = StudentDomain(
            studentId = 1,
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            userId = 10,
            name = "Juan",
            lastName = "Perez",
            secondLastName = "Lopez"
        )

        viewModel.getArguments(student)

        val inputs = viewModel.uiInputs.first()
        val uiState = viewModel.uiState.first()

        assertEquals("Juan", inputs.name.valueText)
        assertEquals("Perez", inputs.lastName.valueText)
        assertEquals("Lopez", inputs.secondLastName.valueText)
        assertEquals("CURP001", inputs.curp.valueText)
        assertEquals("2000-01-01", inputs.birthday.valueText)
        assertEquals("1234567890", inputs.phoneNumber.valueText)
        assertEquals(false, uiState.isNew)
    }
}
