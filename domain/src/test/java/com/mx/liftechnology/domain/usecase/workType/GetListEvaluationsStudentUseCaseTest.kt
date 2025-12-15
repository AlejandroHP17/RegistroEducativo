package com.mx.liftechnology.domain.usecase.workType

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.domain.model.student.EvaluationsStudentDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [GetListEvaluationsStudentUseCase].
 */
class GetListEvaluationsStudentUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var evaluationRepository: EvaluationRepository
    private lateinit var useCase: GetListEvaluationsStudentUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk()
        evaluationRepository = mockk()
        useCase = GetListEvaluationsStudentUseCase(preferenceUseCase, evaluationRepository)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando falta algun dato requerido`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns null
        io.mockk.every { preferenceUseCase.getIdPartial() } returns 1

        val result: ModelResult<List<EvaluationsStudentDomain>, ModelError> = useCase(
            workTypeId = 1,
            studentId = 1,
            formativeFieldId = 1
        )

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando datos son correctos delega en el repositorio`() = runTest {
        io.mockk.every { preferenceUseCase.getIdCycleSchool() } returns 10
        io.mockk.every { preferenceUseCase.getIdPartial() } returns 2
        val domainList = listOf(EvaluationsStudentDomain(workName = "Tarea", workDate = "2024-01-10", grade = 9.0))
        coEvery {
            evaluationRepository.getListEvaluations(
                schoolCycleId = 10,
                partialId = 2,
                formativeFieldId = 3,
                workTypeId = 1,
                studentId = 5,
                workDate = null,
                workDateFrom = null,
                workDateTo = null
            )
        } returns SuccessResult(domainList)

        val result: ModelResult<List<EvaluationsStudentDomain>, ModelError> = useCase(
            workTypeId = 1,
            studentId = 5,
            formativeFieldId = 3
        )

        assertTrue(result is SuccessResult)
        assertEquals(domainList, (result as SuccessResult).data)
    }
}
