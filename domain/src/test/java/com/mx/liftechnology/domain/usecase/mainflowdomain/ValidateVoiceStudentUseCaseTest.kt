package com.mx.liftechnology.domain.usecase.mainflowdomain

import com.mx.liftechnology.domain.model.generic.ModelVoiceConstants
import com.mx.liftechnology.domain.usecase.ValidateVoiceStudentUseCase
import com.mx.liftechnology.domain.usecase.ValidateVoiceStudentUseCaseImp
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.ValidateVoiceStudentUseCase].
 * Esta clase contiene los tests unitarios para el caso de uso que valida y procesa los datos de un estudiante a partir de una entrada de voz.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateVoiceStudentUseCaseTest {

    private lateinit var validateVoiceStudentUseCase: ValidateVoiceStudentUseCase

    /**
     * Configuración inicial para los tests.
     * Se ejecuta antes de cada test para inicializar el [ValidateVoiceStudentUseCase].
     */
    @Before
    fun setUp() {
        validateVoiceStudentUseCase = ValidateVoiceStudentUseCaseImp()
    }

    /**
     * Test para verificar que los datos de un estudiante se procesan correctamente a partir de una entrada de voz válida.
     * Comprueba que todos los campos (nombre, apellidos, CURP, fecha de nacimiento y teléfono) se extraen y formatean como se espera.
     */
    @Test
    fun `buildModelStudent con datos validos`() = runBlocking {
        val voiceInput = "Nombre Juan Apellido paterno Perez Apellido Materno Gomez CURP XAXX010101HXXIXXA0 fecha de nacimiento 1 de enero de 2001 Número de contacto 1234567890"
        val result = validateVoiceStudentUseCase.buildModelStudent(voiceInput)

        assertEquals("Juan", result?.get(ModelVoiceConstants.NAME))
        assertEquals("Perez", result?.get(ModelVoiceConstants.LAST_NAME))
        assertEquals("Gomez", result?.get(ModelVoiceConstants.SECOND_LAST_NAME))
        assertEquals("XAXX010101HXXIXXA0", result?.get(ModelVoiceConstants.CURP))
        assertEquals("2001-01-01", result?.get(ModelVoiceConstants.BIRTHDAY))
        assertEquals("1234567890", result?.get(ModelVoiceConstants.PHONE_NUMBER))
    }
}