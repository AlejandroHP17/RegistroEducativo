package com.mx.liftechnology.registroeducativo.main.ui.menu

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.menu.ControlMenuDomain
import com.mx.liftechnology.domain.model.menu.ControlRegisterDomain
import com.mx.liftechnology.domain.model.schoolCycle.DialogGroupPartialDomain
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.domain.usecase.menu.GetControlMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetControlRegisterUseCase
import com.mx.liftechnology.domain.usecase.menu.GetGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.GetListPartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.SavePartialMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdateGroupMenuUseCase
import com.mx.liftechnology.domain.usecase.menu.UpdatePartialMenuUseCase
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
import org.junit.Before
import org.junit.Test

/**
 * Tests para [MenuViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MenuViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getGroupMenuUseCase: GetGroupMenuUseCase
    private lateinit var updateGroupMenuUseCase: UpdateGroupMenuUseCase
    private lateinit var getControlMenuUseCase: GetControlMenuUseCase
    private lateinit var getControlRegisterUseCase: GetControlRegisterUseCase
    private lateinit var getListPartialMenuUseCase: GetListPartialMenuUseCase
    private lateinit var savePartialMenuUseCase: SavePartialMenuUseCase
    private lateinit var updatePartialMenuUseCase: UpdatePartialMenuUseCase

    private lateinit var viewModel: MenuViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        getGroupMenuUseCase = mockk()
        updateGroupMenuUseCase = mockk(relaxed = true)
        getControlMenuUseCase = mockk()
        getControlRegisterUseCase = mockk()
        getListPartialMenuUseCase = mockk()
        savePartialMenuUseCase = mockk()
        updatePartialMenuUseCase = mockk(relaxed = true)

        viewModel = MenuViewModel(
            dispatcherProvider,
            getGroupMenuUseCase,
            updateGroupMenuUseCase,
            getControlMenuUseCase,
            getControlRegisterUseCase,
            getListPartialMenuUseCase,
            savePartialMenuUseCase,
            updatePartialMenuUseCase
        )
    }

    @Test
    fun `getGroup exito actualiza dialogState y uiState`() = runTest(testDispatcher) {
        val groupItem = DialogStudentGroupDomain(nameGroup = "Grupo A", idGroup = 1, listItemPartial = emptyList(), namePartial = null, itemPartial = null)
        val listGroup = listOf(groupItem)
        val responseDomain = mockk<com.mx.liftechnology.domain.model.menu.GetGroupMenuDomain> {
            coEvery { infoSchoolSelected } returns groupItem
            coEvery { listSchool } returns listGroup
        }

        coEvery { getGroupMenuUseCase.invoke() } returns SuccessResult(responseDomain)
        coEvery { getListPartialMenuUseCase.invoke() } returns SuccessResult(emptyList())
        coEvery { savePartialMenuUseCase.invoke(any()) } returns null
        coEvery { getControlRegisterUseCase.invoke() } returns SuccessResult(emptyList())

        viewModel.getGroup()

        val uiState = viewModel.uiState.first()
        val dialogState = viewModel.dialogState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(groupItem, dialogState.studentGroupItem)
        assertEquals(1, dialogState.studentGroupList.size)
    }

    @Test
    fun `getGroup error actualiza uiState a ERROR`() = runTest(testDispatcher) {
        coEvery { getGroupMenuUseCase.invoke() } returns object : ModelResult<com.mx.liftechnology.domain.model.menu.GetGroupMenuDomain, ModelError> {}

        viewModel.getGroup()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
    }

    @Test
    fun `getControlMenu exito actualiza controlItems y uiState NOTHING`() = runTest(testDispatcher) {
        val controlItems = listOf<ControlMenuDomain>()
        coEvery { getControlMenuUseCase.invoke() } returns SuccessResult(controlItems)

        viewModel.getControlMenu()

        val uiState = viewModel.uiState.first()
        val dataState = viewModel.dataState.first()

        assertEquals(EnumUi.NOTHING, uiState.uiState)
        assertEquals(controlItems, dataState.controlItems)
    }

    @Test
    fun `getControlMenu error actualiza uiState a ERROR`() = runTest(testDispatcher) {
        coEvery { getControlMenuUseCase.invoke() } returns object : ModelResult<List<ControlMenuDomain>, ModelError> {}

        viewModel.getControlMenu()

        val uiState = viewModel.uiState.first()
        assertEquals(EnumUi.ERROR, uiState.uiState)
    }

    @Test
    fun `updateGroup actualiza dialogState y llama use case`() = runTest(testDispatcher) {
        val groupItem = DialogStudentGroupDomain(nameGroup = "Grupo B", idGroup = 2, listItemPartial = emptyList(), namePartial = null, itemPartial = null)

        viewModel.updateGroup(groupItem)

        val dialogState = viewModel.dialogState.first()
        assertEquals(groupItem, dialogState.studentGroupItem)
        coVerify { updateGroupMenuUseCase.invoke(groupItem) }
    }

    @Test
    fun `updatePartial actualiza dialogState y llama use case`() = runTest(testDispatcher) {
        val partialItem = DialogGroupPartialDomain(partialId = 1, name = "Parcial 1")

        viewModel.updatePartial(partialItem)

        val dialogState = viewModel.dialogState.first()
        assertEquals(partialItem, dialogState.studentGroupItem.itemPartial)
        coVerify { updatePartialMenuUseCase.invoke(partialItem) }
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
