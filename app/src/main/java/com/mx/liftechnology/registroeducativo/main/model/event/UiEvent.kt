package com.mx.liftechnology.registroeducativo.main.model.event

/**
 * Clase base sellada para eventos de UI que deben ser manejados una sola vez.
 * Los eventos son diferentes de los estados: los estados representan el estado actual,
 * mientras que los eventos representan acciones que deben ejecutarse una vez.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
sealed class UiEvent {
    /**
     * Evento para navegar a una pantalla específica.
     */
    data class Navigate(val route: String) : UiEvent()

    /**
     * Evento para navegar al home después de un login exitoso.
     */
    object NavigateToHome : UiEvent()

    /**
     * Evento para volver atrás en la navegación.
     */
    object NavigateBack : UiEvent()

    /**
     * Evento para navegar al home después de un login exitoso.
     */
    object NavigateToAdmin : UiEvent()

    /**
     * Evento para mostrar un error.
     */
    data class ShowError(val message: String) : UiEvent()

    /**
     * Evento para mostrar un mensaje de éxito.
     */
    data class ShowSuccess(val message: String) : UiEvent()
}