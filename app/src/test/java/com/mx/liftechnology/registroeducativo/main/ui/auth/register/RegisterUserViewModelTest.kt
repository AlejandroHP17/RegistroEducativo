package com.mx.liftechnology.registroeducativo.main.ui.auth.register

import com.mx.liftechnology.domain.usecase.auth.ValidateFieldsLoginFlowUseCase
import com.mx.liftechnology.domain.usecase.auth.RegisterUserUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Before

/**
 * Tests para [RegisterUserViewModel].
 * Esta clase contiene los tests unitarios para el ViewModel de la pantalla de registro de usuarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterUserViewModelTest {

    private lateinit var registerUserViewModel: RegisterUserViewModel
    private val dispatcherProvider: DispatcherProvider = mockk()
    private val registerUserUseCase: RegisterUserUseCase = mockk()
    private val validateFieldsLoginFlowUseCase: ValidateFieldsLoginFlowUseCase = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterUserViewModel] y sus dependencias.
     */
    @Before
    fun setUp() {
        io.mockk.every { dispatcherProvider.io } returns Dispatchers.Unconfined
        registerUserViewModel = RegisterUserViewModel(dispatcherProvider, registerUserUseCase, validateFieldsLoginFlowUseCase)
    }
}