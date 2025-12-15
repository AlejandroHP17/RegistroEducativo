package com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByStudent

import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.workType.GetListEvaluationsStudentUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListWotyFofiUseCase
import com.mx.liftechnology.registroeducativo.main.model.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.share.ModelSubComplexCard
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiData
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
 * Tests para [WotyByStudentViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WotyByStudentViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getListWotyFofiUseCase: GetListWotyFofiUseCase
    private lateinit var getListEvaluationsStudentUseCase: GetListEvaluationsStudentUseCase
    private lateinit var viewModel: WotyByStudentViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        getListWotyFofiUseCase = mockk()
        getListEvaluationsStudentUseCase = mockk()
        viewModel = WotyByStudentViewModel(dispatcherProvider, getListWotyFofiUseCase, getListEvaluationsStudentUseCase)
    }

    @Test
    fun `updateStudent actualiza uiState`() = runTest(testDispatcher) {
        val student = StudentDomain(
            studentId = 1,
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            userId = 10,
            name = "Juan",
            lastName = "Perez",
            secondLastName = "Lopez"
        )

        viewModel.updateStudent(student)

        val uiState = viewModel.uiState.first()
        assertEquals(student, uiState.student)
    }

    @Test
    fun `getListWotyFofi exito actualiza dataCard`() = runTest(testDispatcher) {
        val domain = WorkTypeByFormativeFieldDomain(works = emptyList())
        coEvery { getListWotyFofiUseCase.invoke() } returns SuccessResult(domain)

        viewModel.getListWotyFofi()

        val data: WotyUiData = viewModel.dataState.first()
        assertEquals(domain.toComplexCardUI(), data.dataCard)
    }

    @Test
    fun `getListWotyFofi error actualiza uiState a ERROR`() = runTest(testDispatcher) {
        coEvery { getListWotyFofiUseCase.invoke() } returns object : ModelResult<WorkTypeByFormativeFieldDomain?, ModelError> {}

        viewModel.getListWotyFofi()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
    }

    @Test
    fun `updateExpandedTitle alterna isExpandedTitle`() = runTest(testDispatcher) {
        val initialCard = ModelComplexCard(idTitle = 1, title = "T1", isExpandedTitle = false, list = emptyList())
        val dataField = WotyByStudentViewModel::class.java.getDeclaredField("_dataState")
        dataField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val flow = dataField.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<WotyUiData>
        flow.value = WotyUiData(dataCard = listOf(initialCard))

        viewModel.updateExpandedTitle(initialCard)

        val updated = viewModel.dataState.first().dataCard?.first()
        assertEquals(true, updated?.isExpandedTitle)
    }
}
