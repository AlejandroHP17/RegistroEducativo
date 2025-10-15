package com.mx.liftechnology.data.repository.flowMain.menu

import android.content.Context
import android.content.res.Resources
import com.mx.liftechnology.data.R
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [MenuLocalRepository].
 * Esta clase contiene los tests unitarios para el repositorio que obtiene los datos del menú local.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class MenuLocalRepositoryTest {

    private lateinit var menuLocalRepository: MenuLocalRepository
    private val context: Context = mockk()
    private val resources: Resources = mockk()

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [MenuLocalRepository] y sus dependencias.
     */
    @Before
    fun setUp() {
        every { context.resources } returns resources
        menuLocalRepository = MenuLocalRepository(context)
    }

    /**
     * Test para verificar que el menú de control se obtiene correctamente.
     */
    @Test
    fun `getControlMenu devuelve la lista correcta`() {
        // Preparamos los datos mockeados
        val controlMenuArray = arrayOf("Control", "Perfil")
        every { resources.getStringArray(R.array.menu_items_control) } returns controlMenuArray

        // Ejecutamos el método a probar
        val result = menuLocalRepository.getControlMenu()

        // Verificamos el resultado
        assertEquals(2, result.size)
        assertEquals("Control", result[0].titleCard)
        assertEquals("Perfil", result[1].titleCard)
    }

    /**
     * Test para verificar que el menú de registro se obtiene correctamente.
     */
    @Test
    fun `getControlRegister devuelve la lista correcta`() {
        // Preparamos los datos mockeados
        val registerMenuArray = arrayOf("Estudiantes", "Materias", "Parciales", "Calendario", "Exportar")
        every { resources.getStringArray(R.array.menu_items_register) } returns registerMenuArray

        // Ejecutamos el método a probar
        val result = menuLocalRepository.getControlRegister()

        // Verificamos el resultado
        assertEquals(5, result.size)
        assertEquals("Estudiantes", result[0].titleCard)
    }
}