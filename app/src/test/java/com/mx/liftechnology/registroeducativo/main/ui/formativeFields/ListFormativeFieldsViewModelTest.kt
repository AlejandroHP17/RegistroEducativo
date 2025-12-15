package com.mx.liftechnology.registroeducativo.main.ui.formativeFields.list

import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.usecase.formativeField.DeleteFormativeFieldsUseCase
import com.mx.liftechnology.domain.usecase.share.GetListFormativeFieldUseCase
import com.mx.liftechnology.registroeducativo.main.mapper.FormativeFieldMapper
import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCard
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
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ListFormativeFieldsViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ListFormativeFieldsViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getListFormativeFieldUseCase: GetListFormativeFieldUseCase
    private lateinit var deleteFormativeFieldsUseCase: DeleteFormativeFieldsUseCase
    private lateinit var viewModel: ListFormativeFieldsViewModel

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
        deleteFormativeFieldsUseCase = mockk()
        viewModel = ListFormativeFieldsViewModel(dispatcherProvider, getListFormativeFieldUseCase, deleteFormativeFieldsUseCase)
    }

    @Test
    fun `getFormativeFields exito actualiza uiState y dataState`() = runTest(testDispatcher) {
        val list = listOf(
            FormativeFieldDomain(formativeFieldID = 1, name = "Lengua", code = "LEN001"),
            FormativeFieldDomain(formativeFieldID = 2, name = "Matemáticas", code = "MAT001")
        )
        coEvery { getListFormativeFieldUseCase.invoke() } returns SuccessResult(list)

        viewModel.getFormativeFields()

        val uiState = viewModel.uiState.first()
        val dataState = viewModel.dataState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(2, dataState.formativeFieldsList?.size)
        assertEquals(
            FormativeFieldMapper.mapFormativeFieldListToCustomCard(dataState.formativeFieldsList, true),
            dataState.formativeFieldsListUI
        )
    }

    @Test
    fun `getFormativeFields error deja lista vacia y estado NOTHING`() = runTest(testDispatcher) {
        coEvery { getListFormativeFieldUseCase.invoke() } returns object : ModelResult<List<FormativeFieldDomain>?, ModelError> {}

        viewModel.getFormativeFields()

        val uiState = viewModel.uiState.first()
        val dataState = viewModel.dataState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(0, dataState.formativeFieldsList?.size ?: 0)
    }

    @Test
    fun `getFormativeFields by card devuelve item correcto`() = runTest(testDispatcher) {
        val list = listOf(
            FormativeFieldDomain(formativeFieldID = 10, name = "Lengua", code = "LEN001")
        )
        coEvery { getListFormativeFieldUseCase.invoke() } returns SuccessResult(list)

        viewModel.getFormativeFields()
        val dataState = viewModel.dataState.first()
        val card = dataState.formativeFieldsListUI?.first() ?: CustomCard(id = 10, numberList = "1", nameCard = "Lengua", isVisibleMenu = true)

        val result: FormativeFieldDomainPar? = viewModel.getFormativeFields(card)

        assertEquals(10, result?.formativeFieldId)
    }

    @Test
    fun `getFormativeFields by card devuelve null si no existe`() = runTest(testDispatcher) {
        val card = CustomCard(id = 999, numberList = "1", nameCard = "X", isVisibleMenu = true)

        val result = viewModel.getFormativeFields(card)

        assertNull(result)
    }

    @Test
    fun `deleteFormativeField exito vuelve a cargar lista`() = runTest(testDispatcher) {
        val list = listOf(
            FormativeFieldDomain(formativeFieldID = 1, name = "Lengua", code = "LEN001")
        )
        coEvery { getListFormativeFieldUseCase.invoke() } returns SuccessResult(list)
        coEvery { deleteFormativeFieldsUseCase.invoke(1) } returns SuccessResult(Unit)

        val card = CustomCard(id = 1, numberList = "1", nameCard = "Lengua", isVisibleMenu = true)

        viewModel.deleteFormativeField(card)

        coVerify(exactly = 1) { deleteFormativeFieldsUseCase.invoke(1) }
        coVerify(atLeast = 1) { getListFormativeFieldUseCase.invoke() }
    }

    @Test
    fun `deleteFormativeField error cambia uiState a NOTHING`() = runTest(testDispatcher) {
        coEvery { deleteFormativeFieldsUseCase.invoke(1) } returns object : ModelResult<Unit, ModelError> {}

        val card = CustomCard(id = 1, numberList = "1", nameCard = "Lengua", isVisibleMenu = true)

        viewModel.deleteFormativeField(card)

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.NOTHING, uiState.uiState)
    }
}
