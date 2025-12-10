package com.mx.liftechnology.data.repositoryImpl.formativeField

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.FormativeFieldApi
import com.mx.liftechnology.core.network.api.ResponseFormativeFieldBulk
import com.mx.liftechnology.core.network.api.ResponseFormativeFields
import com.mx.liftechnology.core.network.api.ResponseGetListFormativeField
import com.mx.liftechnology.core.network.api.ResponseGetListWotyFofi
import com.mx.liftechnology.core.network.api.ResponseWorkTypes
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
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para [FormativeFieldRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de campos formativos.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class FormativeFieldRepositoryImplTest {

    private lateinit var formativeFieldApi: FormativeFieldApi
    private lateinit var formativeFieldRepository: FormativeFieldRepositoryImpl

    @Before
    fun setup() {
        formativeFieldApi = mockk()
        formativeFieldRepository = FormativeFieldRepositoryImpl(formativeFieldApi)
    }

    // ========== Tests para getList ==========

    @Test
    fun `getList con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseList = listOf(
            ResponseGetListFormativeField(
                formativeFieldId = 1,
                name = "Matemáticas",
                code = "MAT001"
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { formativeFieldApi.getListFormativeFields(any()) } returns apiResponse

        // When
        val result = formativeFieldRepository.getList(cycleSchoolId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.size)
        assertEquals("Matemáticas", successResult.data[0].name)
        assertEquals("MAT001", successResult.data[0].code)
    }

    @Test
    fun `getList con lista vacía retorna SuccessResult con lista vacía`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = emptyList<ResponseGetListFormativeField>(),
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { formativeFieldApi.getListFormativeFields(any()) } returns apiResponse

        // When
        val result = formativeFieldRepository.getList(cycleSchoolId = 1)

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.isEmpty())
    }

    @Test
    fun `getList con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<List<ResponseGetListFormativeField>>>(404, responseBody)

        coEvery { formativeFieldApi.getListFormativeFields(any()) } returns response

        // When
        val result = formativeFieldRepository.getList(cycleSchoolId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    // ========== Tests para getListWotyFofi ==========

    @Test
    fun `getListWotyFofi con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseGetListWotyFofi(
            formativeFields = listOf(
                ResponseFormativeFields(
                    formativeFieldId = 1,
                    formativeFieldName = "Matemáticas",
                    code = "MAT001",
                    listWorkTypes = listOf(
                        ResponseWorkTypes(
                            workTypeId = 1,
                            workTypeName = "Examen",
                            evaluationWeight = 50.0
                        )
                    )
                )
            )
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { formativeFieldApi.getListWotyFofi(any(), date) } returns apiResponse

        // When
        val result = formativeFieldRepository.getListWotyFofi(schoolCycleId = 1, date = date)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.formativeFields.size)
        assertEquals("Matemáticas", successResult.data.formativeFields[0].formativeFieldName)
    }

    // ========== Tests para registerBulk ==========

    @Test
    fun `registerBulk con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseFormativeFieldBulk(
            formativeFieldsId = 1,
            formativeFieldsName = "Matemáticas",
            formativeFieldsCode = "MAT001"
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { formativeFieldApi.registerFormativeFieldsBulk(any()) } returns apiResponse

        // When
        val result = formativeFieldRepository.registerBulk(
            cycleSchoolId = 1,
            formativeFieldName = "Matemáticas",
            code = "MAT001",
            workTypes = emptyList(),
            evaluations = emptyList()
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals("Matemáticas", successResult.data.name)
        assertEquals("MAT001", successResult.data.code)
        assertEquals(1, successResult.data.formativeFieldID)
    }

    @Test
    fun `registerBulk con error 409 retorna ErrorResult CONFLICT`() = runTest {
        // Given
        val responseBody = "Conflict".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseFormativeFieldBulk>>(409, responseBody)

        coEvery { formativeFieldApi.registerFormativeFieldsBulk(any()) } returns response

        // When
        val result = formativeFieldRepository.registerBulk(
            cycleSchoolId = 1,
            formativeFieldName = "Matemáticas",
            code = "MAT001",
            workTypes = emptyList(),
            evaluations = emptyList()
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.CONFLICT, (result as ErrorResult).error)
    }

    // ========== Tests para delete ==========

    @Test
    fun `delete con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = "Campo formativo eliminado",
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { formativeFieldApi.deleteFormativeField(any()) } returns apiResponse

        // When
        val result = formativeFieldRepository.delete(fieldId = 1)

        // Then
        assertTrue(result is SuccessResult)
        assertEquals("Campo formativo eliminado", (result as SuccessResult).data)
    }

    @Test
    fun `delete con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<String>>(404, responseBody)

        coEvery { formativeFieldApi.deleteFormativeField(any()) } returns response

        // When
        val result = formativeFieldRepository.delete(fieldId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    @Test
    fun `delete con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { formativeFieldApi.deleteFormativeField(any()) } throws ConnectException("No hay conexión")

        // When
        val result = formativeFieldRepository.delete(fieldId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }
}
