package com.mx.liftechnology.registroeducativo.main.ui.principal

import com.mx.liftechnology.core.util.session.SessionManager
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.TypeToastUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk

/**
 * Tests para [SharedViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SharedViewModelTest {

    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: SharedViewModel

    private val sessionExpiredFlow = MutableSharedFlow<Boolean>()

    @Before
    fun setup() {
        sessionManager = mockk(relaxed = true)
        every { sessionManager.sessionExpired } returns sessionExpiredFlow
        viewModel = SharedViewModel(sessionManager)
    }

    @Test
    fun `init escucha sessionExpired y actualiza estado`() = runTest(UnconfinedTestDispatcher()) {
        assertFalse(viewModel.uiState.value.sessionExpired)

        sessionExpiredFlow.emit(true)

        assertTrue(viewModel.uiState.value.sessionExpired)
    }

    @Test
    fun `sessionExpired llama resetSessionExpired en SessionManager`() = runTest {
        viewModel.sessionExpired()

        coVerify(exactly = 1) { sessionManager.resetSessionExpired() }
    }

    @Test
    fun `showToast actualiza controlToast con valores correctos`() = runTest {
        val messageId = 123

        viewModel.showToast(messageToast = messageId, typeToast = TypeToastUi.ERROR)

        val toast = viewModel.uiState.value.controlToast
        assertEquals(messageId, toast.messageToast)
        assertTrue(toast.showToast)
        assertEquals(TypeToastUi.ERROR, toast.typeToast)
    }

    @Test
    fun `hideToast pone showToast en false`() = runTest {
        viewModel.showToast(messageToast = 123)
        assertTrue(viewModel.uiState.value.controlToast.showToast)

        viewModel.hideToast()

        assertFalse(viewModel.uiState.value.controlToast.showToast)
    }

    @Test
    fun `modifyShowToast sustituye el estado de toast`() = runTest {
        val newToast = ToastUiState(messageToast = 999, showToast = true, typeToast = TypeToastUi.WARNING)

        viewModel.modifyShowToast(newToast)

        val toast = viewModel.uiState.value.controlToast
        assertEquals(999, toast.messageToast)
        assertTrue(toast.showToast)
        assertEquals(TypeToastUi.WARNING, toast.typeToast)
    }
}
