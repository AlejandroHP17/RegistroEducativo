package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.register

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.SpinnersWorkMethodsDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.usecase.formativeField.GetListWorkTypeUseCase
import com.mx.liftechnology.domain.usecase.formativeField.RegisterFormativeFieldsWithValidationUseCase
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
 * Tests para [RegisterFormativeFieldsViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterFormativeFieldsViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var registerFormativeFieldsWithValidationUseCase: RegisterFormativeFieldsWithValidationUseCase
    private lateinit var getListWorkTypeUseCase: GetListWorkTypeUseCase
    private lateinit var viewModel: RegisterFormativeFieldsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        registerFormativeFieldsWithValidationUseCase = mockk()
        getListWorkTypeUseCase = mockk()
        viewModel = RegisterFormativeFieldsViewModel(dispatcherProvider, registerFormativeFieldsWithValidationUseCase, getListWorkTypeUseCase)
    }

    @Test
    fun `onFormativeFieldChanged actualiza estado`() = runTest(testDispatcher) {
        val field = ModelStateOutFieldText(valueText = "Lengua")

        viewModel.onFormativeFieldChanged(field)

        val uiState = viewModel.uiState.first()
        assertEquals(field, uiState.formativeField)
    }

    @Test
    fun `onOptionsChanged crea lista de spinners y opciones`() = runTest(testDispatcher) {
        viewModel.onOptionsChanged("2")

        val uiState = viewModel.uiState.first()
        assertEquals("2", uiState.options.valueText)
        assertEquals(2, uiState.listAdapter?.size)
    }

    @Test
    fun `onNameChange actualiza nombre y workTypeId en posicion`() = runTest(testDispatcher) {
        viewModel.onOptionsChanged("1")
        val workType = WorkTypeDomain(workTypeId = 5, name = "Examen")

        viewModel.onNameChange(workType to 0)

        val uiState = viewModel.uiState.first()
        val item = uiState.listAdapter?.first()
        assertEquals("Examen", item?.name?.valueText)
        assertEquals(5, item?.workTypeId)
    }

    @Test
    fun `onPercentChange actualiza percent en posicion`() = runTest(testDispatcher) {
        viewModel.onOptionsChanged("1")
        val percent = ModelStateOutFieldText(valueText = "50")

        viewModel.onPercentChange(percent to 0)

        val uiState = viewModel.uiState.first()
        val item = uiState.listAdapter?.first()
        assertEquals("50", item?.percent?.valueText)
    }

    @Test
    fun `validateFieldsCompose exito muestra toast success`() = runTest(testDispatcher) {
        viewModel.onFormativeFieldChanged("Lengua".stringToModelStateOutFieldText())
        viewModel.onOptionsChanged("1")

        val listAdapter = listOf(
            SpinnersWorkMethodsDomain(
                position = 0,
                name = "Examen".stringToModelStateOutFieldText(),
                percent = "50".stringToModelStateOutFieldText(),
                workTypeId = 1
            )
        )

        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = SuccessResult(Any()) as ModelResult<Any, ModelError>
        )
        val wrapper = RegisterFormativeFieldsWithValidationUseCase.Result(
            validationResult = validationResult,
            updatedListAdapter = listAdapter
        )

        coEvery { registerFormativeFieldsWithValidationUseCase.invoke(any(), any(), any()) } returns wrapper

        viewModel.validateFieldsCompose()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_register_formative_field, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
    }

    @Test
    fun `validateFieldsCompose ErrorResult muestra toast error`() = runTest(testDispatcher) {
        viewModel.onFormativeFieldChanged("Lengua".stringToModelStateOutFieldText())
        viewModel.onOptionsChanged("1")

        val listAdapter = emptyList<SpinnersWorkMethodsDomain>()
        val modelError: ModelError = mockk()
        val validationResult: ModelValidationResult<Any> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = ErrorResult(modelError) as ModelResult<Any, ModelError>
        )
        val wrapper = RegisterFormativeFieldsWithValidationUseCase.Result(
            validationResult = validationResult,
            updatedListAdapter = listAdapter
        )

        coEvery { registerFormativeFieldsWithValidationUseCase.invoke(any(), any(), any()) } returns wrapper

        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.REGISTER_FORMATIVE_FIELD
            )
        } returns R.string.app_name

        viewModel.validateFieldsCompose()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)
    }

    @Test
    fun `getListWorkType exito actualiza lista de metodos`() = runTest(testDispatcher) {
        val workTypes = listOf(WorkTypeDomain(workTypeId = 1, name = "Examen"))
        coEvery { getListWorkTypeUseCase.invoke() } returns SuccessResult(workTypes)

        viewModel.getListWorkType()

        val uiState = viewModel.uiState.first()
        assertEquals(workTypes, uiState.listWorkMethods)
    }

    @Test
    fun `getListWorkType error asigna item Nuevo`() = runTest(testDispatcher) {
        coEvery { getListWorkTypeUseCase.invoke() } returns object : ModelResult<List<WorkTypeDomain>?, ModelError> {}

        viewModel.getListWorkType()

        val uiState = viewModel.uiState.first()
        assertEquals(1, uiState.listWorkMethods?.size)
        assertEquals(-1, uiState.listWorkMethods?.first()?.workTypeId)
        assertEquals("Nuevo", uiState.listWorkMethods?.first()?.name)
    }

    @Test
    fun `modifyShowToast actualiza flag showToast`() = runTest(testDispatcher) {
        val initial = viewModel.uiState.first()
        assertEquals(false, initial.controlToast.showToast)

        viewModel.modifyShowToast(true)

        val updated = viewModel.uiState.first()
        assertEquals(true, updated.controlToast.showToast)
    }
}
