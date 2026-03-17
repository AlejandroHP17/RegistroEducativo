package com.mx.liftechnology.domain.usecase.menu

import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.model.schoolCycle.DialogStudentGroupDomain
import com.mx.liftechnology.domain.model.schoolCycle.SchoolCycleDomain
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Tests para [UpdateGroupMenuUseCase].
 */
class UpdateGroupMenuUseCaseTest {

    private lateinit var preferenceUseCase: PreferenceUseCase
    private lateinit var useCase: UpdateGroupMenuUseCase

    @Before
    fun setup() {
        preferenceUseCase = mockk(relaxed = true)
        useCase = UpdateGroupMenuUseCase(preferenceUseCase)
    }

    @Test
    fun `guarda id de ciclo escolar cuando item tiene id`() {
        val schoolCycle = SchoolCycleDomain(cycleSchoolId = 10, name = "", cct = "", grade = "", group = "", shiftName = "", periodCatalogId = 1)
        val dialog = DialogStudentGroupDomain(
            selected = true,
            item = schoolCycle,
            nameItem = "",
            listItemPartial = emptyList(),
            itemPartial = null,
            namePartial = null
        )

        useCase(dialog)

        verify(exactly = 1) { preferenceUseCase.setIdCycleSchool(10) }
    }

    @Test
    fun `no guarda nada cuando item es nulo`() {
        val dialog = DialogStudentGroupDomain(
            selected = true,
            item = null,
            nameItem = "",
            listItemPartial = emptyList(),
            itemPartial = null,
            namePartial = null
        )

        useCase(dialog)

        verify(exactly = 0) { preferenceUseCase.setIdCycleSchool(any()) }
    }
}
