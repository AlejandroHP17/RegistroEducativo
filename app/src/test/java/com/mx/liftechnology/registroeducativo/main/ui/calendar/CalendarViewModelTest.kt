package com.mx.liftechnology.registroeducativo.main.ui.calendar

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper
import com.mx.liftechnology.registroeducativo.main.mapper.StudentMapper
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

/**
 * Tests para [CalendarViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CalendarViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getListFormativeFieldUseCase: GetListFormativeFieldUseCase
    private lateinit var getListStudentUseCase: GetListStudentUseCase
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var viewModel: CalendarViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        getListFormativeFieldUseCase = mockk()
        getListStudentUseCase = mockk()
        preferenceUseCase = mockk()
        viewModel = CalendarViewModel(dispatcherProvider, getListFormativeFieldUseCase, getListStudentUseCase, preferenceUseCase)
    }

    @Test
    fun `getFormativeFields exito actualiza estados correctamente`() = runTest(testDispatcher) {
        val formativeFields = listOf(
            FormativeFieldDomain(formativeFieldId = 1, name = "Lengua", description = null, schoolCycleId = 1)
        )
        coEvery { getListFormativeFieldUseCase.invoke() } returns SuccessResult(formativeFields)

        viewModel.getFormativeFields()

        val uiState = viewModel.calendarUiState.first()
        val dataState = viewModel.dataFormativeFieldState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(1, dataState.formativeFieldsList?.size)
        assertEquals(
            FormativeFieldMapper.mapFormativeFieldListToCustomCard(dataState.formativeFieldsList, false),
            dataState.formativeFieldsListUI
        )
    }

    @Test
    fun `getFormativeFields error deja lista vacia y estado NOTHING`() = runTest(testDispatcher) {
        coEvery { getListFormativeFieldUseCase.invoke() } returns object : ModelResult<List<FormativeFieldDomain>?, ModelError> {}

        viewModel.getFormativeFields()

        val uiState = viewModel.calendarUiState.first()
        val dataState = viewModel.dataFormativeFieldState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(0, dataState.formativeFieldsList?.size ?: 0)
    }

    @Test
    fun `getFormativeField devuelve el campo correcto`() = runTest(testDispatcher) {
        val formativeFields = listOf(
            FormativeFieldDomain(formativeFieldId = 1, name = "Lengua", description = null, schoolCycleId = 1)
        )
        coEvery { getListFormativeFieldUseCase.invoke() } returns SuccessResult(formativeFields)

        viewModel.getFormativeFields()
        val dataState = viewModel.dataFormativeFieldState.first()
        val card = dataState.formativeFieldsListUI?.first() ?: CustomCard(id = 1, numberList = "1", nameCard = "Lengua", isVisibleMenu = false)

        val result: FormativeFieldDomainPar? = viewModel.getFormativeField(card)

        assertEquals(1, result?.formativeFieldId)
    }

    @Test
    fun `getFormativeField devuelve null si no existe`() = runTest(testDispatcher) {
        val card = CustomCard(id = 999, numberList = "1", nameCard = "Desconocido", isVisibleMenu = false)

        val result = viewModel.getFormativeField(card)

        assertNull(result)
    }

    @Test
    fun `getListStudent exito actualiza estados correctamente`() = runTest(testDispatcher) {
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

        val uiState = viewModel.calendarUiState.first()
        val dataState = viewModel.dataStudentState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(1, dataState.studentList?.size)
        assertEquals(
            StudentMapper.mapStudentListToCustomCard(dataState.studentList, false),
            dataState.studentListUI
        )
    }

    @Test
    fun `getListStudent error mantiene estado NOTHING`() = runTest(testDispatcher) {
        coEvery { getListStudentUseCase.invoke() } returns object : ModelResult<List<StudentDomain>?, ModelError> {}

        viewModel.getListStudent()

        val uiState = viewModel.calendarUiState.first()
        assertEquals(EnumUi.NOTHING, uiState.uiState)
    }

    @Test
    fun `getStudent devuelve estudiante correcto`() = runTest(testDispatcher) {
        val students = listOf(
            StudentDomain(
                studentId = 5,
                curp = "CURP5",
                birthday = "2000-01-01",
                phoneNumber = "1234567890",
                userId = 1,
                name = "Ana",
                lastName = "Lopez",
                secondLastName = "Garcia"
            )
        )
        coEvery { getListStudentUseCase.invoke() } returns SuccessResult(students)

        viewModel.getListStudent()
        val dataState = viewModel.dataStudentState.first()
        val card = dataState.studentListUI?.first() ?: CustomCard(id = 5, numberList = "1", nameCard = "Ana", isVisibleMenu = false)

        val student = viewModel.getStudent(card)

        assertEquals(5, student?.studentId)
    }

    @Test
    fun `getStudent devuelve null cuando no encuentra`() = runTest(testDispatcher) {
        val card = CustomCard(id = 999, numberList = "1", nameCard = "X", isVisibleMenu = false)

        val student = viewModel.getStudent(card)

        assertNull(student)
    }

    @Test
    fun `setRangeDate actualiza fecha en uiState`() = runTest(testDispatcher) {
        val date = LocalDate.of(2024, 1, 1)

        viewModel.setRangeDate(date)

        val uiState = viewModel.calendarUiState.first()
        assertEquals("2024-01-01", uiState.date)
    }
}
