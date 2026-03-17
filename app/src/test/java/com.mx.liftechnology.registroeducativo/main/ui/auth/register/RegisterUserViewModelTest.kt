package com.mx.liftechnology.registroeducativo.main.ui.auth.register

import android.content.Context
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.auth.RegisterUserWithValidationUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.auth.RegisterUserUiInputs
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterUserViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterUserViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var registerUserWithValidationUseCase: RegisterUserWithValidationUseCase
    private lateinit var viewModel: RegisterUserViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        registerUserWithValidationUseCase = mockk()
        viewModel = RegisterUserViewModel(dispatcherProvider, registerUserWithValidationUseCase)
    }

    @Test
    fun `onXChanged actualiza inputState`() = runTest(testDispatcher) {
        val email = ModelStateOutFieldText(valueText = "user@test.com")
        val pass = ModelStateOutFieldText(valueText = "Password123")
        val repeatPass = ModelStateOutFieldText(valueText = "Password123")
        val code = ModelStateOutFieldText(valueText = "ABC123")

        viewModel.onEmailChanged(email)
        viewModel.onPassChanged(pass)
        viewModel.onRepeatPassChanged(repeatPass)
        viewModel.onCodeChanged(code)

        val input: com.mx.liftechnology.registroeducativo.main.model.auth.RegisterUserUiInputs = viewModel.inputState.first()
        assertEquals(email, input.emailInputState)
        assertEquals(pass, input.passInputState)
        assertEquals(repeatPass, input.repeatPassInputState)
        assertEquals(code, input.codeInputState)
    }

    @Test
    fun `validateFieldsCompose con validacion invalida vuelve uiState a NOTHING y actualiza campos`() = runTest(testDispatcher) {
        // Arrange
        val email = ModelStateOutFieldText(valueText = "user@test.com")
        val pass = ModelStateOutFieldText(valueText = "pass")
        val repeatPass = ModelStateOutFieldText(valueText = "pass")
        val code = ModelStateOutFieldText(valueText = "1234")

        viewModel.onEmailChanged(email)
        viewModel.onPassChanged(pass)
        viewModel.onRepeatPassChanged(repeatPass)
        viewModel.onCodeChanged(code)

        val invalidPass = pass.copy(isError = true)
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.invalid(
            validationStates = mapOf(
                "pass" to invalidPass
            )
        )

        coEvery {
            registerUserWithValidationUseCase.invoke(any(), any(), any(), any())
        } returns validationResult

        // Act
        viewModel.validateFieldsCompose()

        // Assert
        val input = viewModel.inputState.first()
        val uiState = viewModel.uiState.first()

        assertEquals(invalidPass, input.passInputState)
        assertEquals(EnumUi.NOTHING, uiState.uiState)
    }

    @Test
    fun `validateFieldsCompose exito muestra toast success y emite NavigateBack`() = runTest(testDispatcher) {
        // Arrange
        val email = ModelStateOutFieldText(valueText = "user@test.com")
        val pass = ModelStateOutFieldText(valueText = "Password123")
        val repeatPass = ModelStateOutFieldText(valueText = "Password123")
        val code = ModelStateOutFieldText(valueText = "1234")

        viewModel.onEmailChanged(email)
        viewModel.onPassChanged(pass)
        viewModel.onRepeatPassChanged(repeatPass)
        viewModel.onCodeChanged(code)

        val opResult: ModelResult<Any, ModelError> = SuccessResult(Any())
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = mapOf(
                "email" to email,
                "pass" to pass,
                "repeatPass" to repeatPass,
                "code" to code
            ),
            operationResult = opResult
        )

        coEvery {
            registerUserWithValidationUseCase.invoke(
                email.valueText,
                pass.valueText,
                repeatPass.valueText,
                code.valueText
            )
        } returns validationResult

        val events = mutableListOf<UiEvent>()
        val job = launch { viewModel.uiEvent.collect { events.add(it) } }

        // Act
        viewModel.validateFieldsCompose()

        // Assert
        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_register_user, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
        assertTrue(events.any { it is UiEvent.NavigateBack })

        job.cancel()
    }

    @Test
    fun `validateFieldsCompose ErrorResult muestra toast de error`() = runTest(testDispatcher) {
        // Arrange
        val email = ModelStateOutFieldText(valueText = "user@test.com")
        val pass = ModelStateOutFieldText(valueText = "Password123")
        val repeatPass = ModelStateOutFieldText(valueText = "Password123")
        val code = ModelStateOutFieldText(valueText = "1234")

        viewModel.onEmailChanged(email)
        viewModel.onPassChanged(pass)
        viewModel.onRepeatPassChanged(repeatPass)
        viewModel.onCodeChanged(code)

        val modelError: ModelError = mockk()
        val opResult: ModelResult<Any, ModelError> = ErrorResult(modelError)
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = mapOf(
                "email" to email,
                "pass" to pass,
                "repeatPass" to repeatPass,
                "code" to code
            ),
            operationResult = opResult
        )

        coEvery {
            registerUserWithValidationUseCase.invoke(
                email.valueText,
                pass.valueText,
                repeatPass.valueText,
                code.valueText
            )
        } returns validationResult

        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.REGISTER_USER
            )
        } returns R.string.app_name

        // Act
        viewModel.validateFieldsCompose()

        // Assert
        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)

        coVerify(exactly = 1) {
            registerUserWithValidationUseCase.invoke(
                email.valueText,
                pass.valueText,
                repeatPass.valueText,
                code.valueText
            )
        }
    }

    @Test
    fun `modifyShowToast actualiza flag showToast`() = runTest(testDispatcher) {
        val initial = viewModel.uiState.first()
        assertEquals(false, initial.controlToast.showToast)

        viewModel.modifyShowToast(true)

        val updated = viewModel.uiState.first()
        assertEquals(true, updated.controlToast.showToast)
    }

    @Test
    fun `getRules devuelve string con saltos de linea`() {
        val context = mockk<Context>(relaxed = true)
        val rulesArray = arrayOf("Regla 1", "Regla 2")
        every { context.resources?.getStringArray(R.array.rules_pass) } returns rulesArray

        val result = viewModel.getRules(context)

        assertEquals("Regla 1\nRegla 2", result)
    }
}
