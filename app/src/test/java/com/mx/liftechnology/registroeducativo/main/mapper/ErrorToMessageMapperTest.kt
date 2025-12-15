package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.core.util.models.UserError
import com.mx.liftechnology.registroeducativo.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Tests para [ErrorToMessageMapper].
 */
class ErrorToMessageMapperTest {

    @Test
    fun `mapErrorToMessage devuelve mensaje generico por defecto`() {
        val result = ErrorToMessageMapper.mapErrorToMessage(UserError.SHOW_GENERIC_ERROR, ErrorToMessageMapper.ErrorContext.GENERIC)

        assertEquals(R.string.toast_error_generic, result)
    }

    @Test
    fun `mapErrorToMessage devuelve mensaje especifico para REGISTER_USER`() {
        val result = ErrorToMessageMapper.mapErrorToMessage(UserError.SHOW_SPECIFIC_ERROR, ErrorToMessageMapper.ErrorContext.REGISTER_USER)

        assertEquals(R.string.toast_error_register_user, result)
    }

    @Test
    fun `mapErrorToMessage devuelve null para LOGS`() {
        val result = ErrorToMessageMapper.mapErrorToMessage(UserError.LOGS, ErrorToMessageMapper.ErrorContext.GENERIC)

        assertNull(result)
    }

    @Test
    fun `mapErrorToMessage NO_INTERNET devuelve mensaje de sin internet`() {
        val result = ErrorToMessageMapper.mapErrorToMessage(UserError.NO_INTERNET, ErrorToMessageMapper.ErrorContext.LOGIN)

        assertEquals(R.string.toast_error_no_internet, result)
    }

    @Test
    fun `mapErrorToMessage UNAUTHORIZED en LOGIN devuelve mensaje de login`() {
        val result = ErrorToMessageMapper.mapErrorToMessage(UserError.UNAUTHORIZED, ErrorToMessageMapper.ErrorContext.LOGIN)

        assertEquals(R.string.toast_error_login_user, result)
    }
}
