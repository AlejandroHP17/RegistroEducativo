package com.mx.liftechnology.data.repositoryImpl.school

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.ResponseCctSchool
import com.mx.liftechnology.core.network.api.ResponsePeriodCatalog
import com.mx.liftechnology.core.network.api.SchoolApi
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para [SchoolRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de escuelas.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SchoolRepositoryImplTest {

    private lateinit var schoolApi: SchoolApi
    private lateinit var schoolRepository: SchoolRepositoryImpl

    @Before
    fun setup() {
        schoolApi = mockk()
        schoolRepository = SchoolRepositoryImpl(schoolApi)
    }

    // ========== Tests para getCct ==========

    @Test
    fun `getCct con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseCctSchool(
            schoolId = 1,
            cct = "CCT123456",
            schoolTypeId = 1,
            schoolName = "Escuela Primaria",
            shiftName = "Matutino",
            periodCatalog = listOf(
                ResponsePeriodCatalog(
                    id = 1,
                    typeName = "Bimestral",
                    periodNumber = 1
                )
            )
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { schoolApi.getCct(any()) } returns apiResponse

        // When
        val result = schoolRepository.getCct(cct = "CCT123456")

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.id)
        assertEquals("CCT123456", successResult.data.cct)
        assertEquals("Escuela Primaria", successResult.data.schoolName)
        assertEquals("Matutino", successResult.data.shiftName)
        assertEquals(1, successResult.data.periodCatalog.size)
        assertEquals("Bimestral", successResult.data.periodCatalog[0].typeName)
    }

    @Test
    fun `getCct con periodCatalog null retorna lista vacía`() = runTest {
        // Given
        val response = ResponseCctSchool(
            schoolId = 1,
            cct = "CCT123456",
            schoolTypeId = 1,
            schoolName = "Escuela Primaria",
            shiftName = "Matutino",
            periodCatalog = null
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { schoolApi.getCct(any()) } returns apiResponse

        // When
        val result = schoolRepository.getCct(cct = "CCT123456")

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.periodCatalog.isEmpty())
    }

    @Test
    fun `getCct con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseCctSchool>>(404, responseBody)

        coEvery { schoolApi.getCct(any()) } returns response

        // When
        val result = schoolRepository.getCct(cct = "CCT999999")

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    @Test
    fun `getCct con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { schoolApi.getCct(any()) } throws ConnectException("No hay conexión")

        // When
        val result = schoolRepository.getCct(cct = "CCT123456")

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    @Test
    fun `getCct con CCT vacío retorna ErrorResult`() = runTest {
        // Given
        val responseBody = "Bad Request".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseCctSchool>>(400, responseBody)

        coEvery { schoolApi.getCct(any()) } returns response

        // When
        val result = schoolRepository.getCct(cct = "")

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.BAD_REQUEST, (result as ErrorResult).error)
    }
}
