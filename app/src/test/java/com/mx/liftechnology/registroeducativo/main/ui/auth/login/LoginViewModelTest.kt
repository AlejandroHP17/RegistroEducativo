package com.mx.liftechnology.registroeducativo.main.ui.auth.login

import com.mx.liftechnology.domain.usecase.auth.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.auth.LoginUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Before

/**
 * Tests para [LoginViewModel].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private val dispatcherProvider: DispatcherProvider = mockk()
    private val loginUseCase: LoginUseCase = mockk()
    private val validateFieldsLoginFlowUseCase: ValidateFieldsLoginFlowUseCase = mockk()

    @Before
    fun setUp() {
        io.mockk.every { dispatcherProvider.io } returns Dispatchers.Unconfined
        loginViewModel = LoginViewModel(dispatcherProvider, loginUseCase, validateFieldsLoginFlowUseCase)
    }
}