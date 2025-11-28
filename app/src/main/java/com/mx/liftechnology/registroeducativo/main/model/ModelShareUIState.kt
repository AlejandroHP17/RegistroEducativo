package com.mx.liftechnology.registroeducativo.main.model

import com.mx.liftechnology.registroeducativo.R
import com.mx.liftechnology.registroeducativo.main.model.ui.ToastUiState

/**
 * Representa el estado de la UI compartido entre diferentes pantallas.
 *
 * @property controlToast El estado para la visualización de mensajes toast.
 * @author Pelkidev
 * @version 1.0.0
 */
data class ShareUiState(
    val controlToast: ToastUiState = ToastUiState(R.string.app_name, false),
    val sessionExpired: Boolean = false
)
