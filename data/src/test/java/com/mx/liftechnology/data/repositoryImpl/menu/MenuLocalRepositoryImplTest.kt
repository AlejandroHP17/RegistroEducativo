package com.mx.liftechnology.data.repositoryImpl.menu

import android.content.Context
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.data.R
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [MenuLocalRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones del menú local.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuLocalRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var menuLocalRepository: MenuLocalRepositoryImpl

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        menuLocalRepository = MenuLocalRepositoryImpl(context)
    }

    // ========== Tests para getControlMenu ==========

    @Test
    fun `getControlMenu con recursos válidos retorna SuccessResult`() = runTest {
        // Given
        val resources = mockk<android.content.res.Resources>(relaxed = true)
        every { context.resources } returns resources
        every { resources.getStringArray(R.array.menu_items_control) } returns arrayOf(
            "Control",
            "Perfil"
        )

        // When
        val result = menuLocalRepository.getControlMenu()

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(2, successResult.data.size)
        assertEquals("Control", successResult.data[0].titleCard)
        assertEquals("Perfil", successResult.data[1].titleCard)
        assertEquals(R.drawable.ic_control, successResult.data[0].image)
        assertEquals(R.drawable.ic_perfil, successResult.data[1].image)
    }

    @Test
    fun `getControlMenu con array vacío retorna SuccessResult con lista vacía`() = runTest {
        // Given
        val resources = mockk<android.content.res.Resources>(relaxed = true)
        every { context.resources } returns resources
        every { resources.getStringArray(R.array.menu_items_control) } returns emptyArray()

        // When
        val result = menuLocalRepository.getControlMenu()

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.isEmpty())
    }

    @Test
    fun `getControlMenu con excepción retorna ErrorResult CATCH`() = runTest {
        // Given
        every { context.resources } throws RuntimeException("Error al acceder a recursos")

        // When
        val result = menuLocalRepository.getControlMenu()

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.CATCH, (result as ErrorResult).error)
    }

    // ========== Tests para getControlRegister ==========

    @Test
    fun `getControlRegister con recursos válidos retorna SuccessResult`() = runTest {
        // Given
        val resources = mockk<android.content.res.Resources>(relaxed = true)
        every { context.resources } returns resources
        every { resources.getStringArray(R.array.menu_items_register) } returns arrayOf(
            "Estudiantes",
            "Materias",
            "Calendario",
            "Parcial",
            "Exportar"
        )

        // When
        val result = menuLocalRepository.getControlRegister()

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(5, successResult.data.size)
        assertEquals("Estudiantes", successResult.data[0].titleCard)
        assertEquals("Materias", successResult.data[1].titleCard)
        assertEquals("Calendario", successResult.data[2].titleCard)
        assertEquals("Parcial", successResult.data[3].titleCard)
        assertEquals("Exportar", successResult.data[4].titleCard)
        assertEquals(R.drawable.ic_students, successResult.data[0].image)
        assertEquals(R.drawable.ic_formative_field, successResult.data[1].image)
        assertEquals(R.drawable.ic_calendars, successResult.data[2].image)
        assertEquals(R.drawable.ic_partial, successResult.data[3].image)
        assertEquals(R.drawable.ic_export, successResult.data[4].image)
    }

    @Test
    fun `getControlRegister con array vacío retorna SuccessResult con lista vacía`() = runTest {
        // Given
        val resources = mockk<android.content.res.Resources>(relaxed = true)
        every { context.resources } returns resources
        every { resources.getStringArray(R.array.menu_items_register) } returns emptyArray()

        // When
        val result = menuLocalRepository.getControlRegister()

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.isEmpty())
    }

    @Test
    fun `getControlRegister con excepción retorna ErrorResult CATCH`() = runTest {
        // Given
        every { context.resources } throws RuntimeException("Error al acceder a recursos")

        // When
        val result = menuLocalRepository.getControlRegister()

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.CATCH, (result as ErrorResult).error)
    }

    @Test
    fun `getControlRegister con recursos null retorna ErrorResult CATCH`() = runTest {
        // Given
        every { context.resources } returns null

        // When
        val result = menuLocalRepository.getControlRegister()

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.CATCH, (result as ErrorResult).error)
    }
}
