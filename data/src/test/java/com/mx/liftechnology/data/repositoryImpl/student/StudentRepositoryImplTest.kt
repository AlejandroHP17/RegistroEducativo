package com.mx.liftechnology.data.repositoryImpl.student

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.RequestEditStudent
import com.mx.liftechnology.core.network.api.RequestRegisterStudent
import com.mx.liftechnology.core.network.api.ResponseEditStudent
import com.mx.liftechnology.core.network.api.ResponseGetStudent
import com.mx.liftechnology.core.network.api.ResponseRegisterStudent
import com.mx.liftechnology.core.network.api.StudentApi
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Tests para [StudentRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de estudiantes.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class StudentRepositoryImplTest {

    private lateinit var studentApi: StudentApi
    private lateinit var studentRepository: StudentRepositoryImpl

    @Before
    fun setup() {
        studentApi = mockk()
        studentRepository = StudentRepositoryImpl(studentApi)
    }

    // ========== Tests para getStudents ==========

    @Test
    fun `getStudents con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseList = listOf(
            ResponseGetStudent(
                studentId = 1,
                curp = "CURP001",
                name = "Juan",
                lastName = "Pérez",
                secondLastName = "García",
                birthday = "2000-01-01",
                phoneNumber = "1234567890",
                userId = 10,
                schoolCycleId = 1,
                isActive = true
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { studentApi.getListStudents(any()) } returns response

        // When
        val result = studentRepository.getStudents(cycleSchoolId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.size)
        assertEquals(1, successResult.data[0].studentId)
        assertEquals("Juan", successResult.data[0].name)
    }

    @Test
    fun `getStudents con lista vacía retorna SuccessResult con lista vacía`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = emptyList<ResponseGetStudent>(),
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { studentApi.getListStudents(any()) } returns response

        // When
        val result = studentRepository.getStudents(cycleSchoolId = 1)

        // Then
        assertTrue(result is SuccessResult)
        assertTrue((result as SuccessResult).data.isEmpty())
    }

    @Test
    fun `getStudents con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<List<ResponseGetStudent>>>(404, responseBody)

        coEvery { studentApi.getListStudents(any()) } returns response

        // When
        val result = studentRepository.getStudents(cycleSchoolId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    @Test
    fun `getStudents con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { studentApi.getListStudents(any()) } throws ConnectException("No hay conexión")

        // When
        val result = studentRepository.getStudents(cycleSchoolId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }

    // ========== Tests para register ==========

    @Test
    fun `register con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseRegister = ResponseRegisterStudent(
            studentId = 1,
            curp = "CURP001",
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            schoolCycleId = 1,
            isActive = true,
            teacherId = 10,
            createdAt = "2024-01-01"
        )
        val responseGeneric = ResponseGeneric(
            data = responseRegister,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        val requestSlot = slot<RequestRegisterStudent>()
        coEvery { studentApi.registerStudent(capture(requestSlot)) } returns response

        // When
        val result = studentRepository.register(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            teacherId = 10,
            schoolCycleId = 1,
            isActive = true
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data?.studentId)
        assertEquals("Juan", successResult.data?.name)
        assertNotNull(requestSlot.captured)
        assertEquals(true, requestSlot.captured.isActive) // Siempre debe ser true según el código
    }

    @Test
    fun `register siempre establece isActive en true`() = runTest {
        // Given
        val responseRegister = ResponseRegisterStudent(
            studentId = 1,
            curp = "CURP001",
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            schoolCycleId = 1,
            isActive = true,
            teacherId = 10,
            createdAt = "2024-01-01"
        )
        val responseGeneric = ResponseGeneric(
            data = responseRegister,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        val requestSlot = slot<RequestRegisterStudent>()
        coEvery { studentApi.registerStudent(capture(requestSlot)) } returns response

        // When
        studentRepository.register(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            teacherId = 10,
            schoolCycleId = 1,
            isActive = false // Se pasa false pero debe ser true
        )

        // Then
        assertNotNull(requestSlot.captured)
        assertEquals(true, requestSlot.captured.isActive)
    }

    @Test
    fun `register con error 409 retorna ErrorResult CONFLICT`() = runTest {
        // Given
        val responseBody = "Conflict".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseRegisterStudent>>(409, responseBody)

        coEvery { studentApi.registerStudent(any()) } returns response

        // When
        val result = studentRepository.register(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            teacherId = 10,
            schoolCycleId = 1,
            isActive = true
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.CONFLICT, (result as ErrorResult).error)
    }

    // ========== Tests para edit ==========

    @Test
    fun `edit con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val request = RequestEditStudent(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            cycleSchoolId = 1,
            isActive = true,
            teacherId = 10
        )
        val responseEdit = ResponseEditStudent(
            studentId = 1,
            curp = "CURP001",
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            schoolCycleId = 1,
            isActive = true,
            teacherId = 10,
            createdAt = "2024-01-01"
        )
        val responseGeneric = ResponseGeneric(
            data = responseEdit,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { studentApi.editStudent(any(), any()) } returns response

        // When
        val result = studentRepository.edit(request = request, studentId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.studentId)
        assertEquals("Juan", successResult.data.name)
    }

    @Test
    fun `edit con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val request = RequestEditStudent(
            name = "Juan",
            lastName = "Pérez",
            secondLastName = "García",
            curp = "CURP001",
            birthday = "2000-01-01",
            phoneNumber = "1234567890",
            cycleSchoolId = 1,
            isActive = true,
            teacherId = 10
        )
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseEditStudent>>(404, responseBody)

        coEvery { studentApi.editStudent(any(), any()) } returns response

        // When
        val result = studentRepository.edit(request = request, studentId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    // ========== Tests para delete ==========

    @Test
    fun `delete con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseGeneric = ResponseGeneric(
            data = "Estudiante eliminado",
            response = ResponseBasic(code = 200, message = "Success")
        )
        val response = Response.success(responseGeneric)

        coEvery { studentApi.deleteStudent(any()) } returns response

        // When
        val result = studentRepository.delete(studentId = 1)

        // Then
        assertTrue(result is SuccessResult)
        assertEquals("Estudiante eliminado", (result as SuccessResult).data)
    }

    @Test
    fun `delete con error 404 retorna ErrorResult NOT_FOUND`() = runTest {
        // Given
        val responseBody = "Not Found".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<String>>(404, responseBody)

        coEvery { studentApi.deleteStudent(any()) } returns response

        // When
        val result = studentRepository.delete(studentId = 999)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NOT_FOUND, (result as ErrorResult).error)
    }

    @Test
    fun `delete con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { studentApi.deleteStudent(any()) } throws ConnectException("No hay conexión")

        // When
        val result = studentRepository.delete(studentId = 1)

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }
}
