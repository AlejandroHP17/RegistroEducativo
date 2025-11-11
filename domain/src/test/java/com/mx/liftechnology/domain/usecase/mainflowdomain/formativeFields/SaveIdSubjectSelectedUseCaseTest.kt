package com.mx.liftechnology.domain.usecase.mainflowdomain.formativeFields

import com.mx.liftechnology.core.preference.ModelPreference
import com.mx.liftechnology.core.preference.PreferenceUseCase
import com.mx.liftechnology.domain.usecase.formativeField.SaveIdSubjectSelectedUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Tests para [com.mx.liftechnology.domain.usecase.formativeField.SaveIdSubjectSelectedUseCase].
 * Verifica que el ID de la materia seleccionada se guarde correctamente en las preferencias.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SaveIdSubjectSelectedUseCaseTest {

    private lateinit var saveIdSubjectSelectedUseCase: SaveIdSubjectSelectedUseCase
    private val preferenceUseCase: PreferenceUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        saveIdSubjectSelectedUseCase = SaveIdSubjectSelectedUseCase(preferenceUseCase)
    }

    /**
     * Test para verificar que se guarda un ID de materia válido.
     */
    @Test
    fun `invoke con un ID valido lo guarda en preferencias`() {
        // Ejecutamos el caso de uso con un ID válido
        saveIdSubjectSelectedUseCase.invoke(123)

        // Verificamos que se llamó al método para guardar en preferencias con el ID correcto
        verify { preferenceUseCase.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP, 123) }
    }

    /**
     * Test para verificar que se guarda -1 cuando el ID es nulo.
     */
    @Test
    fun `invoke con un ID nulo guarda -1 en preferencias`() {
        // Ejecutamos el caso de uso con un ID nulo
        saveIdSubjectSelectedUseCase.invoke(null)

        // Verificamos que se llamó al método para guardar en preferencias con -1
        verify { preferenceUseCase.savePreferenceInt(ModelPreference.ID_PROFESSOR_TEACHER_SCHOOL_SUBJECT_GROUP, -1) }
    }
}