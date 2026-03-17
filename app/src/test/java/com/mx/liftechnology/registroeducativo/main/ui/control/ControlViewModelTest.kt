package com.mx.liftechnology.registroeducativo.main.ui.control

import com.google.gson.Gson
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.repository.auth.AuthRepository
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import com.mx.liftechnology.domain.repository.partial.PartialRepository
import com.mx.liftechnology.domain.repository.school.SchoolRepository
import com.mx.liftechnology.domain.repository.schoolCycle.SchoolCycleRepository
import com.mx.liftechnology.domain.repository.student.StudentRepository
import com.mx.liftechnology.domain.repository.workType.WorkTypeRepository
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ControlViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ControlViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var authRepository: AuthRepository
    private lateinit var studentRepository: StudentRepository
    private lateinit var formativeFieldRepository: FormativeFieldRepository
    private lateinit var evaluationRepository: EvaluationRepository
    private lateinit var partialRepository: PartialRepository
    private lateinit var schoolRepository: SchoolRepository
    private lateinit var schoolCycleRepository: SchoolCycleRepository
    private lateinit var workTypeRepository: WorkTypeRepository
    private lateinit var viewModel: ControlViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        authRepository = mockk()
        studentRepository = mockk()
        formativeFieldRepository = mockk()
        evaluationRepository = mockk()
        partialRepository = mockk()
        schoolRepository = mockk()
        schoolCycleRepository = mockk()
        workTypeRepository = mockk()

        viewModel = ControlViewModel(
            dispatcherProvider,
            authRepository,
            studentRepository,
            formativeFieldRepository,
            evaluationRepository,
            partialRepository,
            schoolRepository,
            schoolCycleRepository,
            workTypeRepository
        )
    }

    @Test
    fun `updateParameterText actualiza parameterText`() = runTest(testDispatcher) {
        viewModel.updateParameterText("123")

        val uiState = viewModel.uiState.first()
        assertEquals("123", uiState.parameterText)
    }

    @Test
    fun `clearResponse limpia responseJson y isLoading`() = runTest(testDispatcher) {
        viewModel.updateParameterText("algo")
        viewModel.callGetUserData()

        viewModel.clearResponse()

        val uiState = viewModel.uiState.first()
        assertEquals("", uiState.responseJson)
        assertEquals(false, uiState.isLoading)
        assertEquals(null, uiState.error)
    }

    @Test
    fun `callGetUserData exito actualiza responseJson y isLoading false`() = runTest(testDispatcher) {
        coEvery { authRepository.getData() } returns SuccessResult(mockk(relaxed = true))

        viewModel.callGetUserData()

        val uiState = viewModel.uiState.first()
        assertEquals(false, uiState.isLoading)
        // Solo verificamos que haya algún JSON generado
        assert(uiState.responseJson.isNotEmpty())
    }

    @Test
    fun `callGetUserData error actualiza error`() = runTest(testDispatcher) {
        val error: ModelError = mockk()
        coEvery { authRepository.getData() } returns ErrorResult(error)

        viewModel.callGetUserData()

        val uiState = viewModel.uiState.first()
        assertEquals(false, uiState.isLoading)
        assertEquals("getUserData", uiState.error?.endpoint)
    }
}
