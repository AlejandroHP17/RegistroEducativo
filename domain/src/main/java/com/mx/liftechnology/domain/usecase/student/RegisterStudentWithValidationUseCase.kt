package com.mx.liftechnology.domain.usecase.student

import com.mx.liftechnology.domain.model.generic.ModelValidationResult
import com.mx.liftechnology.domain.model.student.StudentDomain
import com.mx.liftechnology.domain.usecase.share.ValidateFieldsStudentUseCase

/**
 * Caso de uso que combina la validación de campos de registro de estudiante con la ejecución del registro.
 * Encapsula la lógica de negocio de validación + operación, retornando los estados de validación
 * para que el ViewModel pueda actualizar la UI.
 *
 * @property validateFieldsUseCase El caso de uso para validar los campos del formulario.
 * @property registerStudentUseCase El caso de uso para ejecutar el registro de estudiante.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class RegisterStudentWithValidationUseCase(
    private val validateFieldsUseCase: ValidateFieldsStudentUseCase,
    private val registerStudentUseCase: RegisterStudentUseCase
) {
    /**
     * Valida los campos del formulario de registro de estudiante y, si son válidos, ejecuta el registro.
     *
     * @param name El nombre del estudiante.
     * @param lastName El apellido paterno del estudiante.
     * @param secondLastName El apellido materno del estudiante.
     * @param curp La CURP del estudiante.
     * @param birthday La fecha de nacimiento del estudiante.
     * @param phoneNumber El número de teléfono del estudiante.
     * @return Un [ModelValidationResult] que contiene:
     * - Los estados de validación de cada campo (para actualizar la UI)
     * - El resultado de la operación de registro (solo si todas las validaciones pasaron)
     * - Un flag que indica si todas las validaciones pasaron
     */
    suspend operator fun invoke(
        name: String?,
        lastName: String?,
        secondLastName: String?,
        curp: String?,
        birthday: String?,
        phoneNumber: String?
    ): ModelValidationResult<StudentDomain?> {
        // 1. Validar todos los campos
        val nameState = validateFieldsUseCase.validateName(name)
        val lastNameState = validateFieldsUseCase.validateLastName(lastName)
        val secondLastNameState = validateFieldsUseCase.validateSecondLastName(secondLastName)
        val curpState = validateFieldsUseCase.validateCurp(curp)
        val birthdayState = validateFieldsUseCase.validateBirthday(birthday)
        val phoneNumberState = validateFieldsUseCase.validatePhoneNumber(phoneNumber)

        val validationStates = mapOf(
            "name" to nameState,
            "lastName" to lastNameState,
            "secondLastName" to secondLastNameState,
            "curp" to curpState,
            "birthday" to birthdayState,
            "phoneNumber" to phoneNumberState
        )

        // 2. Verificar si hay errores de validación
        val hasErrors = nameState.isError || lastNameState.isError || secondLastNameState.isError ||
                curpState.isError || birthdayState.isError || phoneNumberState.isError

        // 3. Si hay errores, retornar resultado de validación fallida
        if (hasErrors) {
            return ModelValidationResult.invalid(validationStates)
        }

        // 4. Si todas las validaciones pasaron, ejecutar la operación
        val operationResult = registerStudentUseCase.invoke(
            name = name ?: "",
            lastName = lastName ?: "",
            secondLastName = secondLastName ?: "",
            curp = curp ?: "",
            birthday = birthday ?: "",
            phoneNumber = phoneNumber ?: ""
        )

        // 5. Retornar resultado con validación exitosa y resultado de la operación
        return ModelValidationResult.valid(validationStates, operationResult)
    }
}

