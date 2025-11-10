package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields

import com.mx.liftechnology.core.network.apiCall.flowMain.formativeField.ResponseGetListFormativeFields
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.data.repository.flowMain.formativeFields.GetListFormativeFieldsRepository
import com.mx.liftechnology.data.util.FailureService
import com.mx.liftechnology.data.util.ResultError
import com.mx.liftechnology.data.util.ResultSuccess
import com.mx.liftechnology.domain.model.generic.ErrorUserResult
import com.mx.liftechnology.domain.model.generic.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListSubjectUseCase].
 * Verifica el comportamiento del caso de uso en diferentes escenarios.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class GetListSubjectUseCaseTest {

    private lateinit var getListSubjectUseCase: GetListSubjectUseCase
    private val getListFormativeFieldsRepository: GetListFormativeFieldsRepository = mockk()
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    /**
     * Configuración inicial para los tests.
     */
    @Before
    fun setUp() {
        getListSubjectUseCase = GetListSubjectUseCase(getListFormativeFieldsRepository, preferenceUseCase)
    }

    /**
     * Test para el flujo de obtención de materias exitoso.
     */
    @Test
    fun `invoke con respuesta exitosa devuelve SuccessState`() = runBlocking {
        // Preparamos el mock
        val mockSubjects = listOf(ResponseGetListFormativeFields(1, "Matemáticas"))
        coEvery { getListFormativeFieldsRepository.executeGetListFormativeFields(any()) } returns ResultSuccess(mockSubjects)

        // Ejecutamos el caso de uso
        val result = getListSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is SuccessResult)
    }

    /**
     * Test para el caso en que el repositorio devuelve una lista vacía.
     */
    @Test
    fun `invoke con lista vacia devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListFormativeFieldsRepository.executeGetListFormativeFields(any()) } returns ResultSuccess(emptyList())

        // Ejecutamos el caso de uso
        val result = getListSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorUserResult)
    }

    /**
     * Test para el caso en que el repositorio devuelve un error.
     */
    @Test
    fun `invoke con error del repositorio devuelve ErrorUserState`() = runBlocking {
        // Preparamos el mock
        coEvery { getListFormativeFieldsRepository.executeGetListFormativeFields(any()) } returns ResultError(FailureService.BadRequest)

        // Ejecutamos el caso de uso
        val result = getListSubjectUseCase.invoke()

        // Verificamos el resultado
        assertTrue(result is ErrorUserResult)
    }
}