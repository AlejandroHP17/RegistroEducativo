package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.data.util.UserError
import com.mx.liftechnology.registroeducativo.R

/**
 * Mapper centralizado para convertir UserError a recursos de string (R.string).
 * Este mapper centraliza la lógica de mapeo de errores a mensajes de UI,
 * evitando que cada ViewModel tenga su propia lógica de mapeo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
object ErrorToMessageMapper {

    /**
     * Mapea un UserError a un recurso de string para mostrar al usuario.
     * Retorna null si el error no debe mostrarse al usuario (ej: LOGS).
     *
     * @param error El error a mapear.
     * @param context El contexto del error (login, register, etc.) para mensajes específicos.
     * @return El recurso de string correspondiente o null si no debe mostrarse.
     */
    fun mapErrorToMessage(error: UserError, context: ErrorContext = ErrorContext.GENERIC): Int? {
        return when (error) {
            UserError.SHOW_GENERIC_ERROR -> getGenericError(context)
            UserError.SHOW_SPECIFIC_ERROR -> getSpecificError(context)
            UserError.SHOW_INCOMPLETE_ERROR -> R.string.toast_error_validate_fields
            UserError.NO_INTERNET -> R.string.toast_error_no_internet
            UserError.UNAUTHORIZED -> getUnauthorizedError(context)
            UserError.USER_NOT_ACTIVE -> R.string.toast_error_inactive_user
            UserError.WITHOUT_ACCESS -> R.string.toast_error_register_partials
            UserError.LOGS -> null // No se muestra al usuario
        }
    }

    /**
     * Obtiene el mensaje de error genérico según el contexto.
     */
    private fun getGenericError(context: ErrorContext): Int {
        return when (context) {
            ErrorContext.LOGIN -> R.string.toast_error_generic
            ErrorContext.REGISTER_USER -> R.string.toast_error_validate_fields
            ErrorContext.REGISTER_STUDENT -> R.string.toast_error_register_student
            ErrorContext.EDIT_STUDENT -> R.string.toast_error_edit_student
            ErrorContext.REGISTER_SCHOOL -> R.string.toast_error_register_school
            ErrorContext.REGISTER_PARTIAL -> R.string.toast_error_register_partials
            ErrorContext.REGISTER_SUBJECT -> R.string.toast_error_register_subject
            ErrorContext.REGISTER_ASSIGNMENT -> R.string.toast_error_register_assignment
            ErrorContext.GENERIC -> R.string.toast_error_generic
        }
    }

    /**
     * Obtiene el mensaje de error específico según el contexto.
     */
    private fun getSpecificError(context: ErrorContext): Int {
        return when (context) {
            ErrorContext.REGISTER_USER -> R.string.toast_error_register_user
            ErrorContext.REGISTER_STUDENT -> R.string.toast_error_register_student
            ErrorContext.EDIT_STUDENT -> R.string.toast_error_edit_student
            ErrorContext.REGISTER_PARTIAL -> R.string.toast_error_register_partials
            else -> R.string.toast_error_generic
        }
    }

    /**
     * Obtiene el mensaje de error de autorización según el contexto.
     */
    private fun getUnauthorizedError(context: ErrorContext): Int {
        return when (context) {
            ErrorContext.LOGIN -> R.string.toast_error_login_user
            else -> R.string.toast_error_generic
        }
    }

    /**
     * Contexto del error para determinar qué mensaje mostrar.
     */
    enum class ErrorContext {
        LOGIN,
        REGISTER_USER,
        REGISTER_STUDENT,
        EDIT_STUDENT,
        REGISTER_SCHOOL,
        REGISTER_PARTIAL,
        REGISTER_SUBJECT,
        REGISTER_ASSIGNMENT,
        GENERIC
    }
}

