package com.mx.liftechnology.registroeducativo.main.model

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ModelStateToastUI

/**
 * Representa el estado de la UI compartido entre diferentes pantallas.
 *
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ModelShareUIState(
    val controlToast: ModelStateToastUI = ModelStateToastUI(R.string.app_name, false),
    val sessionExpired: Boolean = false
)
