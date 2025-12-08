package com.mx.liftechnology.registroeducativo.main.model.ui

/**
 * Representa el estado de un mensaje toast en la UI.
 *
 * @property messageToast El recurso del string del mensaje a mostrar.
 * @property showToast Indica si el toast debe mostrarse.
 * @property typeToast El tipo de toast a mostrar (éxito, error, etc.).
 * @author Pelkidev
 * @version 1.0.0
 */
data class ToastUiState(
    val messageToast: Int,
    val showToast: Boolean,
    val typeToast: ModelStateTypeToastUI = ModelStateTypeToastUI.SUCCESS
)
