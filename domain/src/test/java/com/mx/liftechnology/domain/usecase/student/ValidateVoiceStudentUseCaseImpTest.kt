package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateVoiceStudentUseCaseImp].
 */
class ValidateVoiceStudentUseCaseImpTest {

    private lateinit var useCase: ValidateVoiceStudentUseCase

    @Before
    fun setup() {
        useCase = ValidateVoiceStudentUseCaseImp()
    }

    @Test
    fun `retorna null cuando la entrada es nula o vacia`() = runTest {
        assertNull(useCase.buildModelStudent(null))
        assertNull(useCase.buildModelStudent(""))
    }

    @Test
    fun `extrae y normaliza correctamente todos los campos`() = runTest {
        val input = """Nombre juan
            |Apellido paterno perez
            |Apellido Materno lopez
            |CURP PELJ950101HDFRRN01
            |fecha de nacimiento 1 de enero de 2000
            |Número de contacto 55 1234 5678""".trimMargin()

        val result = useCase.buildModelStudent(input)

        assertNotNull(result)
        result!!
        assertEquals("Juan", result[ModelVoiceConstants.NAME])
        assertEquals("Perez", result[ModelVoiceConstants.LAST_NAME])
        assertEquals("Lopez", result[ModelVoiceConstants.SECOND_LAST_NAME])
        assertEquals("PELJ950101HDFRRN01", result[ModelVoiceConstants.CURP])
        assertEquals("2000-01-01", result[ModelVoiceConstants.BIRTHDAY])
        assertEquals("5512345678", result[ModelVoiceConstants.PHONE_NUMBER])
    }

    @Test
    fun `marca telefono invalido cuando no tiene 10 digitos`() = runTest {
        val input = """Nombre juan
            |Apellido paterno perez
            |Apellido Materno lopez
            |CURP PELJ950101HDFRRN01
            |fecha de nacimiento 1 de enero de 2000
            |Número de contacto 12345""".trimMargin()

        val result = useCase.buildModelStudent(input)

        assertNotNull(result)
        assertEquals("Número inválido", result!![ModelVoiceConstants.PHONE_NUMBER])
    }

    @Test
    fun `marca fecha invalida cuando el formato es incorrecto`() = runTest {
        val input = """Nombre juan
            |Apellido paterno perez
            |Apellido Materno lopez
            |CURP PELJ950101HDFRRN01
            |fecha de nacimiento 2000-01-01
            |Número de contacto 55 1234 5678""".trimMargin()

        val result = useCase.buildModelStudent(input)

        assertNotNull(result)
        assertEquals("Fecha inválida", result!![ModelVoiceConstants.BIRTHDAY])
    }
}
