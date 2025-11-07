package com.mx.liftechnology.domain.usecase.mainflowdomain.subject

import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCodeInputs
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods

/**
 * @file Define el caso de uso para la validación de campos relacionados con una materia.
 * @author Pelkidev
 * @version 1.0.0
 */

/**
 * Interfaz para el caso de uso que valida los campos de una materia.
 * Define los contratos para validar el nombre, las opciones y la lista de métodos de trabajo.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface ValidateFieldsSubjectUseCase {
    /**
     * Valida el nombre de la materia.
     * @param nameSubject El nombre de la materia a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateNameCompose(nameSubject: String?): ModelStateOutFieldText

    /**
     * Valida una opción seleccionada (ej: número de trabajos).
     * @param option La opción a validar.
     * @return Un [ModelStateOutFieldText] con el resultado de la validación.
     */
    fun validateOptionCompose(option: String?): ModelStateOutFieldText

    /**
     * Valida una lista de métodos de trabajo (tareas, exámenes, etc.).
     * Marca como erróneos los campos de nombre o porcentaje que estén vacíos.
     * @param listJobs La lista de métodos de trabajo a validar.
     * @return Una lista mutable con los estados de validación actualizados, o nulo si la entrada es nula.
     */
    fun validateListJobsCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): MutableList<ModelSpinnersWorkMethods>?

    /**
     * Valida que la suma de los porcentajes de todos los métodos de trabajo sea igual a 100.
     * @param listJobs La lista de métodos de trabajo a validar.
     * @return Un [ModelStateOutFieldText] que indica si la suma de porcentajes es correcta.
     */
    fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): ModelStateOutFieldText
}

/**
 * Implementación de [ValidateFieldsSubjectUseCase].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class ValidateFieldsSubjectUseCaseImp : ValidateFieldsSubjectUseCase {
    /**
     * {@inheritDoc}
     */
    override fun validateNameCompose(nameSubject: String?): ModelStateOutFieldText {
        return when {
            nameSubject.isNullOrEmpty() -> nameSubject.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.ET_EMPTY
            )

            else -> nameSubject.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateOptionCompose(option: String?): ModelStateOutFieldText {
        return when {
            option.isNullOrEmpty() -> option.stringToModelStateOutFieldText(
                isError = true,
                errorMessage = ModelCodeInputs.SP_NOT_JOB
            )

            else -> option.stringToModelStateOutFieldText(errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun validateListJobsCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): MutableList<ModelSpinnersWorkMethods>? {
        return listJobs?.map { item ->
            val isNameInvalid = item.name.valueText.isNullOrEmpty()
            val isPercentInvalid = item.percent.valueText.isNullOrEmpty()

            item.copy(
                name = item.name.copy(
                    valueText = item.name.valueText,
                    isError = isNameInvalid,
                    errorMessage = if (isNameInvalid) ModelCodeInputs.SP_NOT_OPTION else ""
                ),
                percent = item.percent.copy(
                    valueText = item.percent.valueText,
                    isError = isPercentInvalid,
                    errorMessage = if (isPercentInvalid) ModelCodeInputs.SP_NOT_JOB else ""
                )
            )
        }?.toMutableList()
    }

    /**
     * {@inheritDoc}
     */
    override fun validPercentCompose(listJobs: MutableList<ModelSpinnersWorkMethods>?): ModelStateOutFieldText {
        return when {
            listJobs?.let { jobs ->
                jobs.all { (it.percent.valueText.toIntOrNull() ?: 0) > 0 } 
                && jobs.sumOf { it.percent.valueText.toIntOrNull() ?: 0 } == 100
            } ?: false -> ModelStateOutFieldText(
                valueText = listJobs?.size.toString(),
                isError = false,
                errorMessage = ModelCodeInputs.ET_CORRECT_FORMAT
            )

            else -> ModelStateOutFieldText(
                valueText = listJobs?.size.toString(),
                isError = true,
                errorMessage = ModelCodeInputs.SP_NOT
            )
        }
    }
}