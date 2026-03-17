package com.mx.liftechnology.registroeducativo.main.ui.auth.login

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.auth.UserDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.auth.LoginWithValidationUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [LoginViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var loginWithValidationUseCase: LoginWithValidationUseCase
    private lateinit var viewModel: LoginViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        loginWithValidationUseCase = mockk()
        viewModel = LoginViewModel(dispatcherProvider, loginWithValidationUseCase)
    }

    @Test
    fun `onEmailChanged y onPassChanged actualizan inputState`() = runTest {
        val emailState = ModelStateOutFieldText(valueText = "user@test.com")
        val passState = ModelStateOutFieldText(valueText = "Password123")

        viewModel.onEmailChanged(emailState)
        viewModel.onPassChanged(passState)
        viewModel.onRememberChanged(true)

        val input = viewModel.inputState.first()
        assertEquals(emailState, input.emailInputState)
        assertEquals(passState, input.passInputState)
        assertTrue(input.isRemember)
    }

    @Test
    fun `cuando validacion falla se actualizan estados de campos y uiState vuelve a NOTHING`() = runTest {
        val invalidEmail = ModelStateOutFieldText(valueText = "", isError = true)
        val okPass = ModelStateOutFieldText(valueText = "Password123", isError = false)

        val validationResult: ModelValidationResult<UserDomain> = ModelValidationResult.invalid(
            mapOf(
                "email" to invalidEmail,
                "pass" to okPass
            )
        )

        coEvery {
            loginWithValidationUseCase.invoke(any(), any(), any())
        } returns validationResult

        viewModel.validateFieldsCompose()

        val input = viewModel.inputState.first()
        val uiState = viewModel.uiState.first()

        assertEquals(invalidEmail, input.emailInputState)
        assertEquals(okPass, input.passInputState)
        assertEquals(EnumUi.NOTHING, uiState.uiState)
    }

    @Test
    fun `cuando login es exitoso muestra toast success y emite evento de navegacion`() = runTest {
        val emailState = ModelStateOutFieldText(valueText = "user@test.com")
        val passState = ModelStateOutFieldText(valueText = "Password123")
        viewModel.onEmailChanged(emailState)
        viewModel.onPassChanged(passState)

        val user = UserDomain(
            email = "user@test.com",
            name = "User",
            lastName = null,
            phone = null,
            isActive = true,
            userId = 1,
            accessLevelId = 1
        )
        val opResult: ModelResult<UserDomain, ModelError> = SuccessResult(user)
        val validationResult: ModelValidationResult<UserDomain> = ModelValidationResult.valid(
            validationStates = mapOf(
                "email" to emailState,
                "pass" to passState
            ),
            operationResult = opResult
        )

        coEvery {
            loginWithValidationUseCase.invoke("user@test.com", "Password123", any())
        } returns validationResult

        val events = mutableListOf<UiEvent>()
        val job = launch { viewModel.uiEvent.collect { events.add(it) } }

        viewModel.validateFieldsCompose()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertTrue(uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_login, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
        assertTrue(events.any { it is UiEvent.NavigateToAdmin })

        job.cancel()
    }

    @Test
    fun `cuando login retorna ErrorResult muestra toast de error`() = runTest {
        val emailState = ModelStateOutFieldText(valueText = "user@test.com")
        val passState = ModelStateOutFieldText(valueText = "Password123")
        viewModel.onEmailChanged(emailState)
        viewModel.onPassChanged(passState)

        val modelError: ModelError = LocalModelError.USER_INCOMPLETE_DATA
        val opResult: ModelResult<UserDomain, ModelError> = ErrorResult(modelError)
        val validationResult: ModelValidationResult<UserDomain> = ModelValidationResult.valid(
            validationStates = mapOf(
                "email" to emailState,
                "pass" to passState
            ),
            operationResult = opResult
        )

        coEvery { loginWithValidationUseCase.invoke("user@test.com", "Password123", any()) } returns validationResult

        // Stub de mapeo de error a mensaje
        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.LOGIN
            )
        } returns R.string.app_name

        viewModel.validateFieldsCompose()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertTrue(uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)

        coVerify(exactly = 1) { loginWithValidationUseCase.invoke("user@test.com", "Password123", any()) }
    }

    @Test
    fun `modifyShowToast actualiza flag showToast`() = runTest {
        val initial = viewModel.uiState.first()
        assertEquals(false, initial.controlToast.showToast)

        viewModel.modifyShowToast(true)
        val updated = viewModel.uiState.first()
        assertTrue(updated.controlToast.showToast)
    }
}
