package com.mx.liftechnology.registroeducativo.main.ui.partial

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.schoolCycle.DatePeriodDomain
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.partial.GetListPartialUseCase
import com.mx.liftechnology.domain.usecase.partial.RegisterPartialWithValidationUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.partial.RegisterPartialUiData
import com.mx.liftechnology.registroeducativo.main.model.partial.RegisterPartialUiState
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
import java.time.LocalDate

/**
 * Tests para [RegisterPartialViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterPartialViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var registerPartialWithValidationUseCase: RegisterPartialWithValidationUseCase
    private lateinit var getListPartialUseCase: GetListPartialUseCase
    private lateinit var viewModel: RegisterPartialViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        registerPartialWithValidationUseCase = mockk()
        getListPartialUseCase = mockk()
        viewModel = RegisterPartialViewModel(dispatcherProvider, registerPartialWithValidationUseCase, getListPartialUseCase)
    }

    @Test
    fun `onPartialChanged crea lista de parciales y rangos`() = runTest(testDispatcher) {
        viewModel.onPartialChanged("3")

        val data: RegisterPartialUiData = viewModel.uiData.first()
        assertEquals("3", data.numberPartials.valueText)
        assertEquals(3, data.listCalendar?.size)
        assertEquals(3, data.rangeDate?.size)
    }

    @Test
    fun `onDateChange actualiza fecha y rango correspondiente`() = runTest(testDispatcher) {
        // preparar 2 parciales
        viewModel.onPartialChanged("2")
        val start = LocalDate.of(2024, 1, 1)
        val end = LocalDate.of(2024, 1, 15)

        viewModel.onDateChange(Pair(Pair(start, end), 1))

        val data = viewModel.uiData.first()
        val calendarItem = data.listCalendar?.get(1)
        val rangeItem = data.rangeDate?.get(1)

        assertEquals("$start / $end", calendarItem?.date?.valueText)
        assertEquals(Pair(start, end), rangeItem)
    }

    @Test
    fun `validateFieldsCompose exito muestra toast y emite NavigateBack`() = runTest(testDispatcher) {
        // Arrange
        viewModel.onPartialChanged("1")
        val listCalendar = listOf(
            DatePeriodDomain(position = 0, date = "2024-01-01 / 2024-01-10".toModelStateOutFieldText(), partialCycleGroup = 0)
        )
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = SuccessResult(Any()) as ModelResult<Any, ModelError>
        )
        val resultWrapper = RegisterPartialWithValidationUseCase.Result(
            validationResult = validationResult,
            updatedListCalendar = listCalendar
        )

        coEvery {
            registerPartialWithValidationUseCase.invoke(any(), any())
        } returns resultWrapper

        val events = mutableListOf<UiEvent>()
        val job = launch { viewModel.uiEvent.collect { events.add(it) } }

        // Act
        viewModel.validateFieldsCompose()

        // Assert
        val uiState: RegisterPartialUiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_register_partial, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
        assertEquals(listCalendar, viewModel.uiData.first().listCalendar)
        assert(events.any { it is UiEvent.NavigateBack })

        job.cancel()
    }

    @Test
    fun `validateFieldsCompose ErrorResult muestra toast error`() = runTest(testDispatcher) {
        // Arrange
        viewModel.onPartialChanged("1")
        val listCalendar = viewModel.uiData.first().listCalendar

        val modelError: ModelError = mockk()
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = ErrorResult(modelError) as ModelResult<Any, ModelError>
        )
        val resultWrapper = RegisterPartialWithValidationUseCase.Result(
            validationResult = validationResult,
            updatedListCalendar = listCalendar
        )

        coEvery {
            registerPartialWithValidationUseCase.invoke(any(), any())
        } returns resultWrapper

        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.REGISTER_PARTIAL
            )
        } returns R.string.app_name

        // Act
        viewModel.validateFieldsCompose()

        // Assert
        val uiState: RegisterPartialUiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)
    }

    @Test
    fun `getListPartialCompose exito actualiza datos y marca read`() = runTest(testDispatcher) {
        val list = listOf(
            DatePeriodDomain(position = 0, date = "2024-01-01 / 2024-01-10", partialCycleGroup = 1)
        )
        coEvery { getListPartialUseCase.invoke() } returns SuccessResult(list)

        viewModel.getListPartialCompose()

        val uiState: RegisterPartialUiState = viewModel.uiState.first()
        val data: RegisterPartialUiData = viewModel.uiData.first()

        assertEquals(false, uiState.isAvailable)
        assertEquals(true, data.read)
        assertEquals(1, data.listCalendar?.size)
        assertEquals("1", data.numberPartials.valueText)
    }

    @Test
    fun `getListPartialCompose error pone uiState en ERROR`() = runTest(testDispatcher) {
        coEvery { getListPartialUseCase.invoke() } returns object : ModelResult<List<DatePeriodDomain>?, ModelError> {}

        viewModel.getListPartialCompose()

        val uiState: RegisterPartialUiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
    }

    @Test
    fun `modifyShowToast actualiza flag showToast`() = runTest(testDispatcher) {
        val initial: RegisterPartialUiState = viewModel.uiState.first()
        assertEquals(false, initial.controlToast.showToast)

        viewModel.modifyShowToast(true)

        val updated: RegisterPartialUiState = viewModel.uiState.first()
        assertEquals(true, updated.controlToast.showToast)
    }
}
