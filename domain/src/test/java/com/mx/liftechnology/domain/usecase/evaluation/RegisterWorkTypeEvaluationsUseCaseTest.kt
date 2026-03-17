package com.mx.liftechnology.domain.usecase.evaluation

import com.mx.liftechnology.core.network.api.RequestListGrades
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.evaluation.CardDomain
import com.mx.liftechnology.domain.repository.evaluation.EvaluationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterWorkTypeEvaluationsUseCase].
 */
class RegisterWorkTypeEvaluationsUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var evaluationRepository: EvaluationRepository
    private lateinit var useCase: RegisterWorkTypeEvaluationsUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk()
        evaluationRepository = mockk()
        useCase = RegisterWorkTypeEvaluationsUseCase(preferenceUseCase, evaluationRepository)
    }

    @Test
    fun `retorna ErrorResult USER_INCOMPLETE_DATA cuando falta algun dato obligatorio`() = runTest {
        // faltan ids en preferencias
        every { preferenceUseCase.getIdFormativeField() } returns null
        every { preferenceUseCase.getIdPartial() } returns 1
        every { preferenceUseCase.getIdCycleSchool() } returns 1

        val result: ModelResult<Boolean, ModelError> = useCase(
            workTypeId = null,
            nameWork = null,
            workDate = null,
            studentListUI = emptyList()
        )

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando datos son validos y repositorio retorna SuccessResult se devuelve true`() = runTest {
        // Given
        every { preferenceUseCase.getIdFormativeField() } returns 10
        every { preferenceUseCase.getIdPartial() } returns 2
        every { preferenceUseCase.getIdCycleSchool() } returns 3

        val cards = listOf(
            CardDomain(studentId = 1, grade = 9.5),
            CardDomain(studentId = 2, grade = 8.0)
        )

        coEvery {
            evaluationRepository.registerWorkTypeEvaluations(
                formativeFieldId = 10,
                partialId = 2,
                workTypeId = 7,
                nameWork = "Tarea 1",
                workDate = "2024-01-10",
                schoolCycleId = 3,
                grades = listOf(
                    RequestListGrades(studentId = 1, grade = 9.5),
                    RequestListGrades(studentId = 2, grade = 8.0)
                )
            )
        } returns SuccessResult(true)

        val result: ModelResult<Boolean, ModelError> = useCase(
            workTypeId = 7,
            nameWork = " Tarea 1 ",
            workDate = " 2024-01-10 ",
            studentListUI = cards
        )

        assertTrue(result is SuccessResult)
        assertEquals(true, (result as SuccessResult).data)

        coVerify(exactly = 1) {
            evaluationRepository.registerWorkTypeEvaluations(
                formativeFieldId = 10,
                partialId = 2,
                workTypeId = 7,
                nameWork = "Tarea 1",
                workDate = "2024-01-10",
                schoolCycleId = 3,
                grades = listOf(
                    RequestListGrades(studentId = 1, grade = 9.5),
                    RequestListGrades(studentId = 2, grade = 8.0)
                )
            )
        }
    }

    @Test
    fun `cuando repositorio retorna ErrorResult se propaga el error`() = runTest {
        every { preferenceUseCase.getIdFormativeField() } returns 10
        every { preferenceUseCase.getIdPartial() } returns 2
        every { preferenceUseCase.getIdCycleSchool() } returns 3

        val cards = emptyList<CardDomain>()

        coEvery { evaluationRepository.registerWorkTypeEvaluations(any(), any(), any(), any(), any(), any(), any()) } returns ErrorResult(NetworkModelError.SERVER_ERROR)

        val result: ModelResult<Boolean, ModelError> = useCase(
            workTypeId = 7,
            nameWork = "Tarea 1",
            workDate = "2024-01-10",
            studentListUI = cards
        )

        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.SERVER_ERROR, (result as ErrorResult).error)
    }

    @Test
    fun `cuando ocurre una excepcion se retorna ErrorResult UNKNOWN`() = runTest {
        every { preferenceUseCase.getIdFormativeField() } returns 10
        every { preferenceUseCase.getIdPartial() } returns 2
        every { preferenceUseCase.getIdCycleSchool() } returns 3

        val cards = emptyList<CardDomain>()

        coEvery { evaluationRepository.registerWorkTypeEvaluations(any(), any(), any(), any(), any(), any(), any()) } throws RuntimeException("error")

        val result: ModelResult<Boolean, ModelError> = useCase(
            workTypeId = 7,
            nameWork = "Tarea 1",
            workDate = "2024-01-10",
            studentListUI = cards
        )

        assertTrue(result is ErrorResult)
        assertEquals(NetworkModelError.UNKNOWN, (result as ErrorResult).error)
    }
}
