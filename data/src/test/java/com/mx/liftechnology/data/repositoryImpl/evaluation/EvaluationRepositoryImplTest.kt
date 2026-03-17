package com.mx.liftechnology.data.repositoryImpl.evaluation

import com.mx.liftechnology.core.model.ResponseBasic
import com.mx.liftechnology.core.model.ResponseGeneric
import com.mx.liftechnology.core.network.api.EvaluationApi
import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.network.api.RequestWorkTypeEvaluations
import com.mx.liftechnology.core.network.api.ResponseGetByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldStudent
import com.mx.liftechnology.core.network.api.ResponseGetListByFieldTypeStudent
import com.mx.liftechnology.core.network.api.ResponseGetListEvaluationsStudent
import com.mx.liftechnology.core.network.api.ResponseGetListWorkStudents
import com.mx.liftechnology.core.network.api.ResponseListWork
import com.mx.liftechnology.core.network.api.ResponseListWorkStudent
import com.mx.liftechnology.core.network.api.ResponseWorkTypeEvaluations
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
 * Tests para [EvaluationRepositoryImpl].
 * 
 * Verifica todos los escenarios posibles de las operaciones de evaluaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class EvaluationRepositoryImplTest {

    private lateinit var evaluationApi: EvaluationApi
    private lateinit var evaluationRepository: EvaluationRepositoryImpl

    @Before
    fun setup() {
        evaluationApi = mockk()
        evaluationRepository = EvaluationRepositoryImpl(evaluationApi)
    }

    // ========== Tests para registerWorkTypeEvaluations ==========

    @Test
    fun `registerWorkTypeEvaluations con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseWorkTypeEvaluations(
            createdWorks = 5,
            totalStudentsWithGrade = 10,
            totalStudentsWithoutGrade = 2,
            formativeFieldName = "Matemáticas",
            partialName = "Parcial 1",
            workTypeId = 1,
            workTypeName = "Examen",
            nameWork = "Examen Final"
        )
        val responseGeneric = ResponseGeneric(
            data = response,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { evaluationApi.registerWorkTypeEvaluations(any()) } returns apiResponse

        // When
        val result = evaluationRepository.registerWorkTypeEvaluations(
            formativeFieldId = 1,
            partialId = 1,
            workTypeId = 1,
            nameWork = "Examen Final",
            workDate = "2024-01-01",
            schoolCycleId = 1,
            grades = emptyList()
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(5, successResult.data.createdWorks)
        assertEquals("Matemáticas", successResult.data.formativeFieldName)
    }

    @Test
    fun `registerWorkTypeEvaluations con error 400 retorna ErrorResult BAD_REQUEST`() = runTest {
        // Given
        val responseBody = "Bad Request".toResponseBody("application/json".toMediaType())
        val response = Response.error<ResponseGeneric<ResponseWorkTypeEvaluations>>(400, responseBody)

        coEvery { evaluationApi.registerWorkTypeEvaluations(any()) } returns response

        // When
        val result = evaluationRepository.registerWorkTypeEvaluations(
            formativeFieldId = 1,
            partialId = 1,
            workTypeId = 1,
            nameWork = "",
            workDate = "",
            schoolCycleId = 1,
            grades = emptyList()
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.BAD_REQUEST, (result as ErrorResult).error)
    }

    // ========== Tests para getListWorkTypeFormativeField ==========

    @Test
    fun `getListWorkTypeFormativeField con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseGetListWorkStudents(
            formativeFieldId = 1,
            nameFormativeField = "Matemáticas",
            listWorks = listOf(
                ResponseListWork(
                    workId = 1,
                    workName = "Examen 1",
                    listWorks = listOf(
                        ResponseListWorkStudent(
                            workStudentId = 1,
                            workStudentName = "Trabajo 1",
                            workStudentDate = "2024-01-01"
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

        coEvery { evaluationApi.getListWorkTypeFormativeField(any()) } returns apiResponse

        // When
        val result = evaluationRepository.getListWorkTypeFormativeField(formativeFieldId = 1)

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.formativeFieldId)
        assertEquals("Matemáticas", successResult.data.nameFormativeField)
        assertEquals(1, successResult.data.listWorks.size)
    }

    // ========== Tests para getListEvaluations ==========

    @Test
    fun `getListEvaluations con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val responseList = listOf(
            ResponseGetListEvaluationsStudent(
                studentId = 1,
                evaluationName = "Examen 1",
                grade = 8.5,
                workDate = "2024-01-01",
                evaluationId = 10
            )
        )
        val responseGeneric = ResponseGeneric(
            data = responseList,
            response = ResponseBasic(code = 200, message = "Success")
        )
        val apiResponse = Response.success(responseGeneric)

        coEvery { 
            evaluationApi.getListEvaluations(
                any(), any(), any(), any(), any(), any(), any(), any()
            ) 
        } returns apiResponse

        // When
        val result = evaluationRepository.getListEvaluations(
            schoolCycleId = 1,
            partialId = 1,
            formativeFieldId = 1,
            workTypeId = 1,
            studentId = 1,
            workDate = null,
            workDateFrom = null,
            workDateTo = null
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.size)
        assertEquals(1, successResult.data[0].studentId)
        assertEquals("Examen 1", successResult.data[0].evaluationName)
    }

    // ========== Tests para getByFieldType ==========

    @Test
    fun `getByFieldType con respuesta exitosa retorna SuccessResult`() = runTest {
        // Given
        val response = ResponseGetByFieldTypeStudent(
            formativeFieldId = 1,
            formativeFieldName = "Matemáticas",
            workTypeId = 1,
            workTypeName = "Examen",
            works = listOf(
                ResponseGetListByFieldTypeStudent(
                    workId = 1,
                    workName = "Examen 1",
                    workDate = "2024-01-01",
                    listStudents = listOf(
                        ResponseGetListByFieldStudent(
                            studentId = 1,
                            studentName = "Juan Pérez",
                            grade = 8.5
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

        coEvery { 
            evaluationApi.getListByFieldTypeStudent(
                any(), any(), any(), any()
            ) 
        } returns apiResponse

        // When
        val result = evaluationRepository.getByFieldType(
            formativeFieldId = 1,
            workTypeId = 1,
            workName = "Examen 1",
            workDate = "2024-01-01"
        )

        // Then
        assertTrue(result is SuccessResult)
        val successResult = result as SuccessResult
        assertEquals(1, successResult.data.formativeFieldId)
        assertEquals("Matemáticas", successResult.data.formativeFieldName)
        assertEquals(1, successResult.data.works.size)
    }

    @Test
    fun `getByFieldType con error de conexión retorna ErrorResult NO_INTERNET`() = runTest {
        // Given
        coEvery { 
            evaluationApi.getListByFieldTypeStudent(
                any(), any(), any(), any()
            ) 
        } throws ConnectException("No hay conexión")

        // When
        val result = evaluationRepository.getByFieldType(
            formativeFieldId = 1,
            workTypeId = 1,
            workName = null,
            workDate = null
        )

        // Then
        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.NO_INTERNET, (result as ErrorResult).error)
    }
}
