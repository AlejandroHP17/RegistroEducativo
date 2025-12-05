package com.mx.liftechnology.registroeducativo.main.model.formativeFields

import com.mx.liftechnology.domain.model.formativeFields.SpinnersWorkMethodsDomain
import com.mx.liftechnology.domain.model.formativeFields.WorkTypeDomain
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.util.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.GenericMapper.toModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState


/**
 * Representa el estado de la UI para la pantalla de registro de materias.
 *
 * @property uiState El estado general de la UI.
 * @property formativeField El estado del campo de entrada del nombre de la materia.
 * @property options El estado del campo de entrada de las opciones.
 * @property listOptions La lista de opciones para el número de métodos de trabajo.
 * @property listWorkMethods La lista de métodos de trabajo disponibles.
 * @property listAdapter La lista de métodos de trabajo seleccionados.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @property read Indica si los campos son de solo lectura.
 * @author Pelkidev
 * @version 1.0.0
 */
data class RegisterFormativeFieldUiState(
    val showControl: Boolean = false,
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val read: Boolean = false,
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),

    val formativeField: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val options: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelCustomSpinner> =
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
            .mapNotNull { it.toModelCustomSpinner() },
    val listAdapter: List<SpinnersWorkMethodsDomain>? = null,
    val listWorkMethods: List<WorkTypeDomain?> = emptyList(),

    )


