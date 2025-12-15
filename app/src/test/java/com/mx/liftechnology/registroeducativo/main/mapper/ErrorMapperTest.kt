package com.mx.liftechnology.registroeducativo.main.mapper

import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.UserError
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests para [ErrorMapper].
 */
class ErrorMapperTest {

    @Test
    fun `mapLocalErrorToUI mapea correctamente los errores locales`() {
        assertEquals(UserError.SHOW_INCOMPLETE_ERROR, ErrorMapper.mapErrorToUI(LocalModelError.USER_INCOMPLETE_DATA))
        assertEquals(UserError.SHOW_GENERIC_ERROR, ErrorMapper.mapErrorToUI(LocalModelError.RESPONSE_INCOMPLETE_DATA))
        assertEquals(UserError.SHOW_GENERIC_ERROR, ErrorMapper.mapErrorToUI(LocalModelError.CATCH))
        assertEquals(UserError.SHOW_GENERIC_ERROR, ErrorMapper.mapErrorToUI(LocalModelError.EMPTY))
    }

    @Test
    fun `mapNetworkErrorToUI mapea correctamente los errores de red`() {
        assertEquals(UserError.UNAUTHORIZED, ErrorMapper.mapErrorToUI(NetworkModelError.UNAUTHORIZED))
        assertEquals(UserError.SHOW_GENERIC_ERROR, ErrorMapper.mapErrorToUI(NetworkModelError.BAD_REQUEST))
        assertEquals(UserError.SHOW_SPECIFIC_ERROR, ErrorMapper.mapErrorToUI(NetworkModelError.CONFLICT))
        assertEquals(UserError.SHOW_SPECIFIC_ERROR, ErrorMapper.mapErrorToUI(NetworkModelError.NOT_FOUND))
        assertEquals(UserError.WITHOUT_ACCESS, ErrorMapper.mapErrorToUI(NetworkModelError.WITHOUT_ACCESS))
        assertEquals(UserError.NO_INTERNET, ErrorMapper.mapErrorToUI(NetworkModelError.NO_INTERNET))
        assertEquals(UserError.SHOW_GENERIC_ERROR, ErrorMapper.mapErrorToUI(NetworkModelError.TIMEOUT))
        assertEquals(UserError.SHOW_GENERIC_ERROR, ErrorMapper.mapErrorToUI(NetworkModelError.SERVER_ERROR))
        assertEquals(UserError.LOGS, ErrorMapper.mapErrorToUI(NetworkModelError.TOO_MANY_REQUESTS))
        assertEquals(UserError.LOGS, ErrorMapper.mapErrorToUI(NetworkModelError.SERIALIZATION))
        assertEquals(UserError.LOGS, ErrorMapper.mapErrorToUI(NetworkModelError.UNKNOWN))
        assertEquals(UserError.USER_NOT_ACTIVE, ErrorMapper.mapErrorToUI(NetworkModelError.NOT_ACTIVE))
        assertEquals(UserError.SHOW_INCOMPLETE_ERROR, ErrorMapper.mapErrorToUI(NetworkModelError.UNKNOWN_REGISTER))
        assertEquals(UserError.LOGS, ErrorMapper.mapErrorToUI(NetworkModelError.EMPTY))
    }
}
