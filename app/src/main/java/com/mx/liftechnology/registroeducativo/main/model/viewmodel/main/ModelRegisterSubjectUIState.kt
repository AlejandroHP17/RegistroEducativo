package com.mx.liftechnology.registroeducativo.main.model.viewmodel.main

import com.mx.liftechnology.core.network.apiCall.flowMain.ResponseGetListAssessmentType
import com.mx.liftechnology.domain.extension.stringToModelStateOutFieldText
import com.mx.liftechnology.domain.model.generic.ModelCustomSpinner
import com.mx.liftechnology.domain.model.generic.ModelStateOutFieldText
import com.mx.liftechnology.domain.model.subject.ModelSpinnersWorkMethods
import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.mapper.DomainToUIMapper.toModelCustomSpinner
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateUIEnum


/**
 * Representa el estado de la UI para la pantalla de registro de materias.
 *
 * @property uiState El estado general de la UI.
 * @property subject El estado del campo de entrada del nombre de la materia.
 * @property options El estado del campo de entrada de las opciones.
 * @property listOptions La lista de opciones para el número de métodos de trabajo.
 * @property listWorkMethods La lista de métodos de trabajo disponibles.
 * @property listAdapter La lista de métodos de trabajo seleccionados.
 * @property controlToast El estado para la visualización de mensajes toast.
 * @property read Indica si los campos son de solo lectura.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelRegisterSubjectStateUI(
    val showControl: Boolean = false,
    val uiState: ModelStateUIEnum = ModelStateUIEnum.NOTHING,
    val read: Boolean = false,
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),

    val subject: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val options: ModelStateOutFieldText = "".stringToModelStateOutFieldText(),
    val listOptions: List<ModelCustomSpinner> =
        listOf(
            "1".toModelCustomSpinner(),
            "2".toModelCustomSpinner(),
            "3".toModelCustomSpinner(),
            "4".toModelCustomSpinner(),
            "5".toModelCustomSpinner(),
            "6".toModelCustomSpinner(),
            "7".toModelCustomSpinner(),
            "8".toModelCustomSpinner(),
            "9".toModelCustomSpinner()
        ),
    val listAdapter: List<ModelSpinnersWorkMethods>? = null,
    val listWorkMethods: List<ResponseGetListAssessmentType?> = emptyList(),

    )


