package com.mx.liftechnology.registroeducativo.main.ui.flowMain.partial

import com.mx.liftechnology.domain.usecase.schoolCycle.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.RegisterListPartialUseCase
import com.mx.liftechnology.domain.usecase.schoolCycle.partial.ValidateFieldsRegisterPartialUseCase
import com.mx.liftechnology.registroeducativo.main.ui.schoolCycle.partial.RegisterPartialViewModel
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Before

/**
 * Tests para [RegisterPartialViewModel].
 * Esta clase contiene los tests unitarios para el ViewModel de la pantalla de registro de parciales, 
 * verificando que la lógica de negocio y la interacción con los casos de uso funcionen correctamente.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterPartialViewModelTest {

    private lateinit var registerPartialViewModel: RegisterPartialViewModel
    private val dispatcherProvider: DispatcherProvider = mockk()
    private val validateFieldsRegisterPartialUseCase: ValidateFieldsRegisterPartialUseCase = mockk()
    private val registerListPartialUseCase: RegisterListPartialUseCase = mockk()
    private val getListPartialUseCase: GetListPartialUseCase = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [RegisterPartialViewModel] y sus dependencias.
     */
    @Before
    fun setUp() {
        io.mockk.every { dispatcherProvider.io } returns Dispatchers.Unconfined
        registerPartialViewModel = RegisterPartialViewModel(
            dispatcherProvider,
            validateFieldsRegisterPartialUseCase,
            registerListPartialUseCase,
            getListPartialUseCase
        )
    }
}