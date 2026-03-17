package com.mx.liftechnology.registroeducativo.main.model.control

/**
 * Datos de los botones de servicios disponibles.
 */
data class ApiServiceButton(
    val title: String,
    val description: String,
    val parameterHint: String,
    val onClick: () -> Unit
)

/**
 * Estado de la UI para la pantalla de control de APIs.
 */
data class ApiControlUiState(
    val parameterText: String = "",
    val responseJson: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
