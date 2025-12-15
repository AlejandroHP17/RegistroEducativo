package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.domain.model.formativeFields.SpinnersWorkMethodsDomain
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [ValidateFieldsFormativeFieldsUseCaseImp].
 *
 * Se cubren los escenarios principales de validación de campos de materia:
 * - Nombre de la materia (validación de no vacío)
 * - Opción (validación de no vacío)
 * - Lista de métodos de trabajo (validación de nombre y porcentaje)
 * - Validación de porcentajes (suma debe ser 100)
 */
class ValidateFieldsFormativeFieldsUseCaseImpTest {

    private lateinit var useCase: ValidateFieldsFormativeFieldsUseCase

    @Before
    fun setup() {
        useCase = ValidateFieldsFormativeFieldsUseCaseImp()
    }

    // ========== validateNameCompose ==========

    @Test
    fun `validateNameCompose retorna error ET_EMPTY cuando el nombre es nulo o vacio`() {
        val resultNull: ModelStateOutFieldText = useCase.validateNameCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateNameCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.ET_EMPTY, resultEmpty.errorMessage)
    }

    @Test
    fun `validateNameCompose retorna formato correcto cuando el nombre tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateNameCompose("Matemáticas")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateOptionCompose ==========

    @Test
    fun `validateOptionCompose retorna error SP_NOT_JOB cuando la opcion es nula o vacia`() {
        val resultNull: ModelStateOutFieldText = useCase.validateOptionCompose(null)
        val resultEmpty: ModelStateOutFieldText = useCase.validateOptionCompose("")

        assertTrue(resultNull.isError)
        assertEquals(ModelCodeInputs.SP_NOT_JOB, resultNull.errorMessage)

        assertTrue(resultEmpty.isError)
        assertEquals(ModelCodeInputs.SP_NOT_JOB, resultEmpty.errorMessage)
    }

    @Test
    fun `validateOptionCompose retorna formato correcto cuando la opcion tiene contenido`() {
        val result: ModelStateOutFieldText = useCase.validateOptionCompose("5")

        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    // ========== validateListJobsCompose ==========

    @Test
    fun `validateListJobsCompose retorna null cuando la lista es null`() {
        val result: MutableList<SpinnersWorkMethodsDomain>? = useCase.validateListJobsCompose(null)

        assertNull(result)
    }

    @Test
    fun `validateListJobsCompose marca como error los trabajos sin nombre o porcentaje`() {
        // Given
        val listJobs = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 1,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Tarea", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 2,
                workTypeId = 2,
                name = ModelStateOutFieldText(valueText = "", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "30", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 3,
                workTypeId = 3,
                name = ModelStateOutFieldText(valueText = "Examen", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = null, isError = false, errorMessage = "")
            )
        )

        // When
        val result: MutableList<SpinnersWorkMethodsDomain>? = useCase.validateListJobsCompose(listJobs)

        // Then
        assertNotNull(result)
        assertEquals(3, result!!.size)
        assertFalse(result[0].name.isError) // Tiene nombre válido
        assertFalse(result[0].percent.isError) // Tiene porcentaje válido
        assertTrue(result[1].name.isError) // Nombre vacío
        assertEquals(ModelCodeInputs.SP_NOT_OPTION, result[1].name.errorMessage)
        assertTrue(result[2].percent.isError) // Porcentaje nulo
        assertEquals(ModelCodeInputs.SP_NOT_JOB, result[2].percent.errorMessage)
    }

    @Test
    fun `validateListJobsCompose no marca error cuando todos los trabajos tienen nombre y porcentaje`() {
        // Given
        val listJobs = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 1,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Tarea", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 2,
                workTypeId = 2,
                name = ModelStateOutFieldText(valueText = "Examen", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            )
        )

        // When
        val result: MutableList<SpinnersWorkMethodsDomain>? = useCase.validateListJobsCompose(listJobs)

        // Then
        assertNotNull(result)
        assertEquals(2, result!!.size)
        assertFalse(result[0].name.isError)
        assertFalse(result[0].percent.isError)
        assertFalse(result[1].name.isError)
        assertFalse(result[1].percent.isError)
    }

    // ========== validPercentCompose ==========

    @Test
    fun `validPercentCompose retorna error SP_NOT cuando la suma no es 100`() {
        // Given
        val listJobs = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 1,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Tarea", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 2,
                workTypeId = 2,
                name = ModelStateOutFieldText(valueText = "Examen", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "30", isError = false, errorMessage = "")
            )
        )

        // When
        val result: ModelStateOutFieldText = useCase.validPercentCompose(listJobs)

        // Then
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.SP_NOT, result.errorMessage)
    }

    @Test
    fun `validPercentCompose retorna error SP_NOT cuando hay porcentajes invalidos o cero`() {
        // Given
        val listJobs = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 1,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Tarea", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "0", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 2,
                workTypeId = 2,
                name = ModelStateOutFieldText(valueText = "Examen", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "100", isError = false, errorMessage = "")
            )
        )

        // When
        val result: ModelStateOutFieldText = useCase.validPercentCompose(listJobs)

        // Then
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.SP_NOT, result.errorMessage)
    }

    @Test
    fun `validPercentCompose retorna formato correcto cuando la suma es 100`() {
        // Given
        val listJobs = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 1,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Tarea", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 2,
                workTypeId = 2,
                name = ModelStateOutFieldText(valueText = "Examen", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            )
        )

        // When
        val result: ModelStateOutFieldText = useCase.validPercentCompose(listJobs)

        // Then
        assertFalse(result.isError)
        assertEquals(ModelCodeInputs.ET_CORRECT_FORMAT, result.errorMessage)
    }

    @Test
    fun `validPercentCompose retorna error SP_NOT cuando la lista es null`() {
        // When
        val result: ModelStateOutFieldText = useCase.validPercentCompose(null)

        // Then
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.SP_NOT, result.errorMessage)
    }

    @Test
    fun `validPercentCompose retorna error SP_NOT cuando hay porcentajes no numericos`() {
        // Given
        val listJobs = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 1,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Tarea", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "abc", isError = false, errorMessage = "")
            ),
            SpinnersWorkMethodsDomain(
                position = 2,
                workTypeId = 2,
                name = ModelStateOutFieldText(valueText = "Examen", isError = false, errorMessage = ""),
                percent = ModelStateOutFieldText(valueText = "50", isError = false, errorMessage = "")
            )
        )

        // When
        val result: ModelStateOutFieldText = useCase.validPercentCompose(listJobs)

        // Then
        assertTrue(result.isError)
        assertEquals(ModelCodeInputs.SP_NOT, result.errorMessage)
    }
}
