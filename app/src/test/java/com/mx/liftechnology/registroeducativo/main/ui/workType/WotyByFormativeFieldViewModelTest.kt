package com.mx.liftechnology.registroeducativo.main.ui.workType.wotyByFormativeField

import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeByFormativeFieldDomain
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.share.SaveFormativeFieldIdSelectedUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListByFieldTypeStudentUseCase
import com.mx.liftechnology.domain.usecase.workType.GetListWorkEvaluationFormativeFieldUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.main.model.share.ModelComplexCard
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.workType.WotyUiData
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [WotyByFormativeFieldViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WotyByFormativeFieldViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getListWorkEvaluationFormativeFieldUseCase: GetListWorkEvaluationFormativeFieldUseCase
    private lateinit var getListByFieldTypeStudentUseCase: GetListByFieldTypeStudentUseCase
    private lateinit var saveFormativeFieldIdSelectedUseCase: SaveFormativeFieldIdSelectedUseCase
    private lateinit var viewModel: WotyByFormativeFieldViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        getListWorkEvaluationFormativeFieldUseCase = mockk()
        getListByFieldTypeStudentUseCase = mockk()
        saveFormativeFieldIdSelectedUseCase = mockk(relaxed = true)
        viewModel = WotyByFormativeFieldViewModel(
            dispatcherProvider,
            getListWorkEvaluationFormativeFieldUseCase,
            getListByFieldTypeStudentUseCase,
            saveFormativeFieldIdSelectedUseCase
        )
    }

    @Test
    fun `updateFormativeField guarda id y actualiza uiState`() = runTest(testDispatcher) {
        val par = FormativeFieldDomainPar(formativeFieldId = 1, name = "Lengua", description = null, schoolCycleId = 1)

        viewModel.updateFormativeField(par)

        val uiState = viewModel.uiState.first()
        assertEquals(par, uiState.formativeFields)
        coVerify { saveFormativeFieldIdSelectedUseCase.invoke(1) }
    }

    @Test
    fun `getListWotyFormativeField exito actualiza dataCard`() = runTest(testDispatcher) {
        val par = FormativeFieldDomainPar(formativeFieldId = 1, name = "Lengua", description = null, schoolCycleId = 1)
        val domain = WorkTypeByFormativeFieldDomain(
            works = emptyList()
        )
        coEvery { getListWorkEvaluationFormativeFieldUseCase.invoke(1) } returns SuccessResult(domain)

        viewModel.getListWotyFormativeField(par)

        val data: WotyUiData = viewModel.dataState.first()
        // Solo comprobamos que se haya ejecutado el mapeo (aunque sea lista vacía)
        assertEquals(domain.toComplexCardUI(), data.dataCard)
    }

    @Test
    fun `updateExpandedTitle alterna isExpandedTitle`() = runTest(testDispatcher) {
        val initialCard = ModelComplexCard(idTitle = 1, title = "T1", isExpandedTitle = false, list = emptyList())
        // Seed inicial
        val dataField = WotyByFormativeFieldViewModel::class.java.getDeclaredField("_dataState")
        dataField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val flow = dataField.get(viewModel) as kotlinx.coroutines.flow.MutableStateFlow<WotyUiData>
        flow.value = WotyUiData(dataCard = listOf(initialCard))

        viewModel.updateExpandedTitle(initialCard)

        val updated = viewModel.dataState.first().dataCard?.first()
        assertEquals(true, updated?.isExpandedTitle)
    }
}
