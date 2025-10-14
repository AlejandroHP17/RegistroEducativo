package com.mx.liftechnology.domain.usecase.mainflowdomain.menu

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGroupTeacher
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.menu.MenuRepository
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.SuccessState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetGroupMenuUseCase].
 * Esta clase contiene los tests unitarios para el caso de uso que obtiene los grupos del menú,
 * verificando que la lógica de negocio funcione como se espera en casos de éxito y error.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetGroupMenuUseCaseTest {

    private lateinit var getGroupMenuUseCase: GetGroupMenuUseCase
    private val menuRepository: MenuRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [GetGroupMenuUseCase] y sus dependencias.
     */
    @Before
    fun setUp() {
        getGroupMenuUseCase = GetGroupMenuUseCase(menuRepository, preferenceUseCase)
    }

    /**
     * Test para verificar el caso de éxito de la obtención de grupos.
     * Se simula una respuesta exitosa del repositorio con una lista no vacía.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos una respuesta mockeada con datos válidos
        val mockGroupTeacher = ResponseGroupTeacher(
            cct = "123",
            schoolYearId = 1,
            description = "Grupo A",
            cycleSchoolId = 1,
            group = "A",
            name = "Primaria",
            nameSchool = "Escuela Test",
            teacherId = 1,
            teacherSchoolCycleGroupId = 1,
            type = "tipo",
            shift = "matutino"
        )
        val mockList = listOf(mockGroupTeacher)

        // Configuramos el mock del repositorio para que devuelva una lista con contenido
        coEvery { menuRepository.executeGetGroup(any()) } returns ResultSuccess(mockList)

        // Ejecutamos el método a probar
        val result = getGroupMenuUseCase.invoke()

        // Verificamos que el resultado sea exitoso
        assertTrue("El resultado debería ser SuccessState, pero fue ${result::class.simpleName}", result is SuccessState)
    }
}