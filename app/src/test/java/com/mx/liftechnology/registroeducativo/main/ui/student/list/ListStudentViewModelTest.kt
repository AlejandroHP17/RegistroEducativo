package com.mx.liftechnology.registroeducativo.main.ui.student.list

import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.share.GetListStudentUseCase
import com.mx.liftechnology.domain.usecase.student.DeleteStudentUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.StudentMapper
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
import com.mx.liftechnology.registroeducativo.main.model.student.ListStudentUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ListStudentViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ListStudentViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getListStudentUseCase: GetListStudentUseCase
    private lateinit var deleteStudentUseCase: DeleteStudentUseCase
    private lateinit var viewModel: ListStudentViewModel

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
        deleteStudentUseCase = mockk()
        viewModel = ListStudentViewModel(dispatcherProvider, getListStudentUseCase, deleteStudentUseCase)
    }

    @Test
    fun `getListStudent exito actualiza uiState y dataState`() = runTest {
        // Arrange
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
            ),
            StudentDomain(
                studentId = 2,
                curp = "CURP2",
                birthday = "2000-01-02",
                phoneNumber = "0987654321",
                userId = 2,
                name = "Ana",
                lastName = "Gomez",
                secondLastName = "Martinez"
            )
        )
        coEvery { getListStudentUseCase.invoke() } returns SuccessResult(students)

        // Act
        viewModel.getListStudent()

        // Assert
        val uiState = viewModel.uiState.first()
        val dataState = viewModel.dataState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(2, dataState.studentList?.size)
        assertEquals(
            StudentMapper.mapStudentListToCustomCard(dataState.studentList, true),
            dataState.studentListUI
        )
        coVerify(exactly = 1) { getListStudentUseCase.invoke() }
    }

    @Test
    fun `getListStudent error deja lista vacia y estado NOTHING`() = runTest {
        // Arrange: cualquier resultado que no sea SuccessResult se considera error
        coEvery { getListStudentUseCase.invoke() } returns object : com.mx.liftechnology.core.util.models.ModelResult<List<StudentDomain>?, com.mx.liftechnology.core.util.models.ModelError> {}

        // Act
        viewModel.getListStudent()

        // Assert
        val uiState = viewModel.uiState.first()
        val dataState = viewModel.dataState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertTrue(dataState.studentList?.isEmpty() ?: true)
    }

    @Test
    fun `getStudent devuelve estudiante correcto a partir de CustomCard`() = runTest {
        // Arrange
        val students = listOf(
            StudentDomain(
                studentId = 10,
                curp = "CURP10",
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

        val dataState = viewModel.dataState.first()
        val card = dataState.studentListUI?.first() ?: CustomCard(id = 10, numberList = "1", nameCard = "Juan Perez", isVisibleMenu = true)

        // Act
        val student = viewModel.getStudent(card)

        // Assert
        assertEquals(10, student?.studentId)
    }

    @Test
    fun `getStudent devuelve null cuando no encuentra coincidencia`() = runTest {
        // Arrange
        val card = CustomCard(id = 999, numberList = "1", nameCard = "Desconocido", isVisibleMenu = true)

        // Act
        val student = viewModel.getStudent(card)

        // Assert
        assertNull(student)
    }

    @Test
    fun `deleteStudent exito vuelve a cargar lista estudiantes`() = runTest {
        // Arrange
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
        coEvery { deleteStudentUseCase.invoke(1) } returns SuccessResult(Unit)

        val card = CustomCard(id = 1, numberList = "1", nameCard = "Juan Perez", isVisibleMenu = true)

        // Act
        viewModel.deleteStudent(card)

        // Assert
        coVerify(exactly = 1) { deleteStudentUseCase.invoke(1) }
        coVerify(atLeast = 1) { getListStudentUseCase.invoke() }
    }

    @Test
    fun `deleteStudent error cambia uiState a NOTHING`() = runTest {
        // Arrange
        coEvery { deleteStudentUseCase.invoke(1) } returns object : com.mx.liftechnology.core.util.models.ModelResult<Unit, com.mx.liftechnology.core.util.models.ModelError> {}

        val card = CustomCard(id = 1, numberList = "1", nameCard = "Juan Perez", isVisibleMenu = true)

        // Act
        viewModel.deleteStudent(card)

        // Assert
        val uiState: ListStudentUiState = viewModel.uiState.first()
        assertEquals(EnumUi.NOTHING, uiState.uiState)
    }
}

