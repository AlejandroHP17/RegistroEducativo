package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsStudentUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterStudentWithValidationUseCase].
 *
 * Se valida que:
 * - Cuando hay errores de validación, no se ejecuta el registro y se retorna un resultado inválido.
 * - Cuando las validaciones pasan, se ejecuta el registro y el resultado se propaga correctamente.
 */
class RegisterStudentWithValidationUseCaseTest {

    private lateinit var validateFieldsStudentUseCase: ValidateFieldsStudentUseCase
    private lateinit var registerStudentUseCase: RegisterStudentUseCase
    private lateinit var registerStudentWithValidationUseCase: RegisterStudentWithValidationUseCase

    @Before
    fun setup() {
        validateFieldsStudentUseCase = mockk()
        registerStudentUseCase = mockk()
        registerStudentWithValidationUseCase = RegisterStudentWithValidationUseCase(
            validateFieldsUseCase = validateFieldsStudentUseCase,
            registerStudentUseCase = registerStudentUseCase
        )
    }

    @Test
    fun `cuando hay errores de validacion no se ejecuta el registro y el resultado es invalido`() = runTest {
        // Given
        val invalidNameState = ModelStateOutFieldText(
            valueText = "",
            isError = true,
            errorMessage = ModelCodeInputs.ET_EMPTY
        )
        val validLastNameState = ModelStateOutFieldText(
            valueText = "Pérez",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validSecondLastNameState = ModelStateOutFieldText(
            valueText = "López",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validCurpState = ModelStateOutFieldText(
            valueText = "PELJ950101HDFRRN01",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validBirthdayState = ModelStateOutFieldText(
            valueText = "1995-01-01",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validPhoneNumberState = ModelStateOutFieldText(
            valueText = "5512345678",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateFieldsStudentUseCase.validateName(null) } returns invalidNameState
        every { validateFieldsStudentUseCase.validateLastName("Pérez") } returns validLastNameState
        every { validateFieldsStudentUseCase.validateSecondLastName("López") } returns validSecondLastNameState
        every { validateFieldsStudentUseCase.validateCurp("PELJ950101HDFRRN01") } returns validCurpState
        every { validateFieldsStudentUseCase.validateBirthday("1995-01-01") } returns validBirthdayState
        every { validateFieldsStudentUseCase.validatePhoneNumber("5512345678") } returns validPhoneNumberState

        // When
        val result: ModelValidationResult<StudentDomain?> = registerStudentWithValidationUseCase.invoke(
            name = null,
            lastName = "Pérez",
            secondLastName = "López",
            curp = "PELJ950101HDFRRN01",
            birthday = "1995-01-01",
            phoneNumber = "5512345678"
        )

        // Then
        assertFalse(result.isValid)
        assertNull(result.operationResult)

        assertEquals(6, result.validationStates.size)
        assertTrue(result.validationStates["name"]!!.isError)
        assertFalse(result.validationStates["lastName"]!!.isError)
        assertFalse(result.validationStates["secondLastName"]!!.isError)
        assertFalse(result.validationStates["curp"]!!.isError)
        assertFalse(result.validationStates["birthday"]!!.isError)
        assertFalse(result.validationStates["phoneNumber"]!!.isError)

        coVerify(exactly = 0) { registerStudentUseCase.invoke(any(), any(), any(), any(), any(), any()) }
    }

    @Test
    fun `cuando las validaciones son correctas se ejecuta el registro y el resultado es valido`() = runTest {
        // Given
        val validNameState = ModelStateOutFieldText(
            valueText = "Juan",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validLastNameState = ModelStateOutFieldText(
            valueText = "Pérez",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validSecondLastNameState = ModelStateOutFieldText(
            valueText = "López",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validCurpState = ModelStateOutFieldText(
            valueText = "PELJ950101HDFRRN01",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validBirthdayState = ModelStateOutFieldText(
            valueText = "1995-01-01",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )
        val validPhoneNumberState = ModelStateOutFieldText(
            valueText = "5512345678",
            isError = false,
            errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
        )

        every { validateFieldsStudentUseCase.validateName("Juan") } returns validNameState
        every { validateFieldsStudentUseCase.validateLastName("Pérez") } returns validLastNameState
        every { validateFieldsStudentUseCase.validateSecondLastName("López") } returns validSecondLastNameState
        every { validateFieldsStudentUseCase.validateCurp("PELJ950101HDFRRN01") } returns validCurpState
        every { validateFieldsStudentUseCase.validateBirthday("1995-01-01") } returns validBirthdayState
        every { validateFieldsStudentUseCase.validatePhoneNumber("5512345678") } returns validPhoneNumberState

        val studentDomain = StudentDomain(
            studentId = 1,
            curp = "PELJ950101HDFRRN01",
            birthday = "1995-01-01",
            phoneNumber = "5512345678",
            userId = 10,
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López"
        )

        val registerResult: ModelResult<StudentDomain?, ModelError> = SuccessResult(studentDomain)

        coEvery {
            registerStudentUseCase.invoke(
                name = "Juan",
                lastName = "Pérez",
                secondLastName = "López",
                curp = "PELJ950101HDFRRN01",
                birthday = "1995-01-01",
                phoneNumber = "5512345678"
            )
        } returns registerResult

        // When
        val result: ModelValidationResult<StudentDomain?> = registerStudentWithValidationUseCase.invoke(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "López",
            curp = "PELJ950101HDFRRN01",
            birthday = "1995-01-01",
            phoneNumber = "5512345678"
        )

        // Then
        assertTrue(result.isValid)
        assertNotNull(result.operationResult)
        assertTrue(result.operationResult is SuccessResult<*>)
        assertEquals(studentDomain, (result.operationResult as SuccessResult).data)

        assertEquals(6, result.validationStates.size)
        assertFalse(result.validationStates["name"]!!.isError)
        assertFalse(result.validationStates["lastName"]!!.isError)
        assertFalse(result.validationStates["secondLastName"]!!.isError)
        assertFalse(result.validationStates["curp"]!!.isError)
        assertFalse(result.validationStates["birthday"]!!.isError)
        assertFalse(result.validationStates["phoneNumber"]!!.isError)

        coVerify(exactly = 1) {
            registerStudentUseCase.invoke(
                name = "Juan",
                lastName = "Pérez",
                secondLastName = "López",
                curp = "PELJ950101HDFRRN01",
                birthday = "1995-01-01",
                phoneNumber = "5512345678"
            )
        }
    }
}
