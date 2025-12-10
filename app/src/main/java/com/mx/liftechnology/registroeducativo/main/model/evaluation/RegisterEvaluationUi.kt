package com.mx.liftechnology.registroeducativo.main.model.evaluation

import com.mx.liftechnology.registroeducativo.main.model.formativeFields.FormativeFieldDomainPar
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.main.model.student.StudentDomainPar
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState
import com.mx.liftechnology.registroeducativo.main.model.ui.EnumUi
import com.mx.liftechnology.registroeducativo.main.model.share.CustomCardStudent

/**
 * Representa el estado de la UI para la pantalla de registro de evaluaciones.
 *
 * @property uiState El estado general de la UI (cargando, éxito, error, etc.).
 * @property controlToast El estado para la visualización de mensajes toast.
 * @property formativeField El campo formativo (materia) seleccionado para la evaluación.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterEvaluationUiState(
    val uiState: EnumUi = EnumUi.NOTHING,
    val controlToast : ToastUiState = ToastUiState(R.string.app_name,false),
    val formativeField : FormativeFieldDomainPar? = null,
)

/**
 * Representa los datos de la UI para la pantalla de registro de evaluaciones.
 *
 * @property nameJob El nombre del trabajo o actividad.
 * @property date La fecha de la evaluación.
 * @property listOptions La lista de opciones de tipos de trabajo disponibles.
 * @property options El tipo de trabajo seleccionado.
 * @property nameAssignment El nombre de la asignación o tipo de trabajo seleccionado.
 * @property studentList La lista de estudiantes del dominio.
 * @property studentListUI La lista de estudiantes formateada para la UI con sus calificaciones.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterEvaluationUiData (
    val nameJob : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val date : ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelCustomSpinner> ?= listOf(),
    val options: ModelCustomSpinner? = null,

    val nameAssignment: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val studentList: List<StudentDomainPar>? = null,
    val studentListUI: List<CustomCardStudent> = emptyList(),
)