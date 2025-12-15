package com.mx.liftechnology.registroeducativo.main.ui.evaluation

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.RegisterEvaluationDomain
import com.mx.liftechnology.domain.model.evaluation.WorkTypeDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.evaluation.GetDatesActivePartialUseCase
import com.mx.liftechnology.domain.usecase.evaluation.GetWorkTypeByFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.evaluation.RegisterEvaluationWithValidationUseCase
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.share.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorMapper
import com.mx.liftechnology.registroeducativo.main.mapper.ErrorToMessageMapper
import com.mx.liftechnology.registroeducativo.main.model.event.UiEvent
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
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
 * Tests para [RegisterEvaluationViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class RegisterEvaluationViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getListStudentUseCase: GetListStudentUseCase
    private lateinit var saveFormativeFieldIdSelectedUseCase: SaveFormativeFieldIdSelectedUseCase
    private lateinit var getWorkTypeByFormativeFieldUseCase: GetWorkTypeByFormativeFieldUseCase
    private lateinit var getDatesActivePartialUseCase: GetDatesActivePartialUseCase
    private lateinit var registerEvaluationWithValidationUseCase: RegisterEvaluationWithValidationUseCase
    private lateinit var viewModel: RegisterEvaluationViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        getListStudentUseCase = mockk()
        saveFormativeFieldIdSelectedUseCase = mockk(relaxed = true)
        getWorkTypeByFormativeFieldUseCase = mockk()
        getDatesActivePartialUseCase = mockk()
        registerEvaluationWithValidationUseCase = mockk()
        viewModel = RegisterEvaluationViewModel(
            dispatcherProvider,
            getListStudentUseCase,
            saveFormativeFieldIdSelectedUseCase,
            getWorkTypeByFormativeFieldUseCase,
            getDatesActivePartialUseCase,
            registerEvaluationWithValidationUseCase
        )
    }

    @Test
    fun `updateFormativeField guarda id y carga workTypes`() = runTest(testDispatcher) {
        val formativeField = FormativeFieldDomainPar(formativeFieldId = 1, name = "Lengua", description = null, schoolCycleId = 1)

        coEvery { getWorkTypeByFormativeFieldUseCase.invoke() } returns SuccessResult(
            listOf(WorkTypeDomain(workTypeId = 1, name = "Tarea", description = null, formativeFieldId = 1))
        )

        viewModel.updateFormativeField(formativeField)

        coVerify(exactly = 1) { saveFormativeFieldIdSelectedUseCase.invoke(1) }
        val uiState = viewModel.uiState.first()
        assertEquals(formativeField, uiState.formativeField)
        val dataState = viewModel.dataState.first()
        assertEquals(1, dataState.listOptions?.size)
    }

    @Test
    fun `getListStudent exito llena studentList y studentListUI`() = runTest(testDispatcher) {
        val students = listOf(
            StudentDomain(
                studentId = 1,
                curp = "CURP1",
                birthday = "2000-01-01",
                phoneNumber = "1234567890",
                userId = 1,
                name = "Juan",
                lastName = "Perez",
                secondLastName = "Lopez"
            )
        )
        coEvery { getListStudentUseCase.invoke() } returns SuccessResult(students)

        viewModel.getListStudent()

        val uiState = viewModel.uiState.first()
        val dataState = viewModel.dataState.first()
        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(1, dataState.studentList?.size)
        assertEquals(1, dataState.studentListUI.size)
    }

    @Test
    fun `onNameChanged actualiza nameJob`() = runTest(testDispatcher) {
        val state = ModelStateOutFieldText(valueText = "Tarea 1")

        viewModel.onNameChanged(state)

        val dataState = viewModel.dataState.first()
        assertEquals(state, dataState.nameJob)
    }

    @Test
    fun `onDateChanged actualiza dialogState`() = runTest(testDispatcher) {
        viewModel.onDateChanged("2024-01-01")

        val dialog = viewModel.dialogState.first()
        assertEquals("2024-01-01", dialog.date.valueText)
    }

    @Test
    fun `onNameAssignmentChanged actualiza nameAssignment y options`() = runTest(testDispatcher) {
        val assignment = ModelCustomSpinner(id = 1, value = "Examen")

        viewModel.onNameAssignmentChanged(assignment)

        val dataState = viewModel.dataState.first()
        assertEquals("Examen", dataState.nameAssignment.valueText)
        assertEquals(assignment, dataState.options)
    }

    @Test
    fun `onScoreChange actualiza score del estudiante correcto`() = runTest(testDispatcher) {
        // Prepara lista de estudiantes en UI
        val students = listOf(
            StudentDomain(
                studentId = 1,
                curp = "CURP1",
                birthday = "2000-01-01",
                phoneNumber = "1234567890",
                userId = 1,
                name = "Juan",
                lastName = "Perez",
                secondLastName = "Lopez"
            )
        )
        coEvery { getListStudentUseCase.invoke() } returns SuccessResult(students)

        viewModel.getListStudent()
        val before = viewModel.dataState.first().studentListUI.first()

        viewModel.onScoreChange(Pair(before.id, "9.5"))

        val after = viewModel.dataState.first().studentListUI.first()
        assertEquals("9.5", after.score.valueText)
    }

    @Test
    fun `validateFields exito muestra toast y emite NavigateBack`() = runTest(testDispatcher) {
        // Prepara datos mínimos
        val students = emptyList<StudentDomain>()
        coEvery { getListStudentUseCase.invoke() } returns SuccessResult(students)
        viewModel.getListStudent()

        viewModel.onNameChanged(ModelStateOutFieldText(valueText = "Tarea"))
        viewModel.onNameAssignmentChanged(ModelCustomSpinner(id = 1, value = "Examen"))
        viewModel.onDateChanged("2024-01-01")

        val opResult: ModelResult<List<RegisterEvaluationDomain>, ModelError> = SuccessResult(emptyList())
        val validationResult: ModelValidationResult<RegisterEvaluationDomain> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = opResult
        )

        coEvery {
            registerEvaluationWithValidationUseCase.invoke(any(), any(), any(), any(), any())
        } returns validationResult

        val events = mutableListOf<UiEvent>()
        val job = launch { viewModel.uiEvent.collect { events.add(it) } }

        viewModel.validateFields()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.SUCCESS, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(R.string.toast_success_register_assignment, uiState.controlToast.messageToast)
        assertEquals(TypeToastUi.SUCCESS, uiState.controlToast.typeToast)
        assert(events.any { it is UiEvent.NavigateBack })

        job.cancel()
    }

    @Test
    fun `validateFields ErrorResult muestra toast error`() = runTest(testDispatcher) {
        viewModel.onNameChanged(ModelStateOutFieldText(valueText = "Tarea"))
        viewModel.onNameAssignmentChanged(ModelCustomSpinner(id = 1, value = "Examen"))
        viewModel.onDateChanged("2024-01-01")

        val modelError: ModelError = mockk()
        val opResult: ModelResult<List<RegisterEvaluationDomain>, ModelError> = ErrorResult(modelError)
        val validationResult: ModelValidationResult<RegisterEvaluationDomain> = ModelValidationResult.valid(
            validationStates = emptyMap(),
            operationResult = opResult
        )

        coEvery {
            registerEvaluationWithValidationUseCase.invoke(any(), any(), any(), any(), any())
        } returns validationResult

        every { ErrorMapper.mapErrorToUI(modelError) } returns com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR
        every {
            ErrorToMessageMapper.mapErrorToMessage(
                error = com.mx.liftechnology.core.util.models.UserError.SHOW_GENERIC_ERROR,
                context = ErrorToMessageMapper.ErrorContext.REGISTER_ASSIGNMENT
            )
        } returns R.string.app_name

        viewModel.validateFields()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
        assertEquals(true, uiState.controlToast.showToast)
        assertEquals(TypeToastUi.ERROR, uiState.controlToast.typeToast)
    }

    @Test
    fun `updateDates actualiza rangeDate en dialogState`() = runTest(testDispatcher) {
        val dates = Pair("2024-01-01", "2024-01-31")
        coEvery { getDatesActivePartialUseCase.invoke() } returns dates

        viewModel.updateDates()

        val dialog = viewModel.dialogState.first()
        assertEquals(dates, dialog.rangeDate)
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
