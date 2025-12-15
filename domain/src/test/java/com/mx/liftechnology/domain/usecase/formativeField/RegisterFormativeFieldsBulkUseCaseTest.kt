package com.mx.liftechnology.domain.usecase.formativeField

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.core.util.models.ErrorResult
import com.mx.liftechnology.core.util.models.LocalModelError
import com.mx.liftechnology.core.util.models.ModelError
import com.mx.liftechnology.core.util.models.ModelResult
import com.mx.liftechnology.core.util.models.NetworkModelError
import com.mx.liftechnology.core.util.models.SuccessResult
import com.mx.liftechnology.domain.model.formativeFields.FormativeFieldDomain
import com.mx.liftechnology.domain.model.formativeFields.SpinnersWorkMethodsDomain
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.repository.formativeFields.FormativeFieldRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests para [RegisterFormativeFieldsBulkUseCase].
 */
class RegisterFormativeFieldsBulkUseCaseTest {

    private lateinit var formativeFieldRepository: FormativeFieldRepository
    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: RegisterFormativeFieldsBulkUseCase

    @Before
    fun setup() {
        formativeFieldRepository = mockk()
        preferenceUseCase = mockk()
        useCase = RegisterFormativeFieldsBulkUseCase(formativeFieldRepository, preferenceUseCase)
    }

    @Test
    fun `retorna USER_INCOMPLETE_DATA cuando faltan ids o lista es vacia`() = runTest {
        every { preferenceUseCase.getIdPartial() } returns null
        every { preferenceUseCase.getIdCycleSchool() } returns null

        val result: ModelResult<FormativeFieldDomain, ModelError> = useCase(null, "Matematicas")

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.USER_INCOMPLETE_DATA, (result as ErrorResult).error)
    }

    @Test
    fun `cuando registro exitoso con data devuelve SuccessResult`() = runTest {
        every { preferenceUseCase.getIdPartial() } returns 1
        every { preferenceUseCase.getIdCycleSchool() } returns 2
        val list = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 0,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Examen"),
                percent = ModelStateOutFieldText(valueText = "100")
            )
        )
        val domain = FormativeFieldDomain(id = 1, name = "Matematicas", isActive = true)
        coEvery { formativeFieldRepository.registerBulk(any(), any(), any(), any(), any()) } returns SuccessResult(domain)

        val result: ModelResult<FormativeFieldDomain, ModelError> = useCase(list, " Matematicas ")

        assertTrue(result is SuccessResult)
        assertEquals(domain, (result as SuccessResult).data)
    }

    @Test
    fun `cuando registro exitoso pero data nula devuelve EMPTY`() = runTest {
        every { preferenceUseCase.getIdPartial() } returns 1
        every { preferenceUseCase.getIdCycleSchool() } returns 2
        val list = mutableListOf(
            SpinnersWorkMethodsDomain(
                position = 0,
                workTypeId = 1,
                name = ModelStateOutFieldText(valueText = "Examen"),
                percent = ModelStateOutFieldText(valueText = "100")
            )
        )
        coEvery { formativeFieldRepository.registerBulk(any(), any(), any(), any(), any()) } returns SuccessResult(null)

        val result: ModelResult<FormativeFieldDomain, ModelError> = useCase(list, "Matematicas")

        assertTrue(result is ErrorResult)
        assertEquals(LocalModelError.EMPTY, (result as ErrorResult).error)
    }
}
