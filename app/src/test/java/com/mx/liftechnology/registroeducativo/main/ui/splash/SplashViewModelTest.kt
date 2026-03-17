package com.mx.liftechnology.registroeducativo.main.ui.splash

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.registroeducativo.main.util.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

/**
 * Tests para [SplashViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var viewModel: SplashViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        dispatcherProvider = object : DispatcherProvider {
            override val main = testDispatcher
            override val io = testDispatcher
            override val default = testDispatcher
            override val unconfined = testDispatcher
        }
        preferenceUseCase = mockk()
        viewModel = SplashViewModel(dispatcherProvider, preferenceUseCase)
    }

    @Test
    fun `onPermissionDenied establece navigate en null`() = runTest {
        // Act
        viewModel.onPermissionDenied()

        // Assert
        assertEquals(null, viewModel.navigate.first())
    }

    @Test
    fun `onPermissionGranted navega al menu cuando usuario esta logeado`() = runTest {
        // Arrange
        coEvery { preferenceUseCase.getRememberLogin() } returns true

        // Act
        viewModel.onPermissionGranted()

        // Assert
        assertEquals(true, viewModel.navigate.first())
        coVerify(exactly = 1) { preferenceUseCase.getRememberLogin() }
    }

    @Test
    fun `onPermissionGranted navega al login cuando usuario no esta logeado`() = runTest {
        // Arrange
        coEvery { preferenceUseCase.getRememberLogin() } returns false

        // Act
        viewModel.onPermissionGranted()

        // Assert
        assertEquals(false, viewModel.navigate.first())
        coVerify(exactly = 1) { preferenceUseCase.getRememberLogin() }
    }
}

