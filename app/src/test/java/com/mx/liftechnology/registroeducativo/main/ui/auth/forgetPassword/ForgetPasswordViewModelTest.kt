package com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword

import android.content.Context
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.usecase.share.ValidateAuthFieldsUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.ui.auth.forgetPassword.ForgetPasswordViewModel
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
 * Tests para [ForgetPasswordViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ForgetPasswordViewModelTest {

    private lateinit var validateAuthFieldsUseCase: ValidateAuthFieldsUseCase
    private lateinit var viewModel: ForgetPasswordViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        validateAuthFieldsUseCase = mockk()
        viewModel = ForgetPasswordViewModel(validateAuthFieldsUseCase)
    }

    @Test
    fun `onEmailChanged actualiza emailState`() = runTest(testDispatcher) {
        // Arrange
        val emailState = ModelStateOutFieldText(valueText = "test@example.com")

        // Act
        viewModel.onEmailChanged(emailState)

        // Assert
        assertEquals(emailState, viewModel.emailState.first())
    }

    @Test
    fun `validateFieldsCompose actualiza emailState con resultado de use case`() = runTest(testDispatcher) {
        // Arrange
        val originalState = ModelStateOutFieldText(valueText = "test@example.com", isError = true)
        val validatedState = ModelStateOutFieldText(valueText = "test@example.com", isError = false)

        viewModel.onEmailChanged(originalState)
        every { validateAuthFieldsUseCase.validateEmailCompose("test@example.com") } returns validatedState

        // Act
        viewModel.validateFieldsCompose()

        // Assert
        assertEquals(validatedState, viewModel.emailState.first())
    }

    @Test
    fun `getRules construye string con saltos de linea`() {
        // Arrange
        val context = mockk<Context>(relaxed = true)
        val rulesArray = arrayOf("Regla 1", "Regla 2", "Regla 3")
        every { context.resources?.getStringArray(R.array.rules_forget_pass) } returns rulesArray

        // Act
        val result = viewModel.getRules(context)

        // Assert
        assertEquals("Regla 1\nRegla 2\nRegla 3", result)
    }
}
