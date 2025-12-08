/**
 * @file Define el gestor de sesión para notificar eventos de expiración de sesión en toda la aplicación.
 * @author Pelkidev
 * @version 1.0.0
 */
package com.mx.liftechnology.core.util.session

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Gestor de sesión que permite notificar y observar eventos de expiración de sesión en toda la aplicación.
 * 
 * **Propósito:**
 * Esta clase centraliza la gestión de eventos de expiración de sesión, permitiendo que diferentes
 * componentes de la aplicación (interceptores de red, ViewModels, etc.) notifiquen y reaccionen
 * a eventos de sesión expirada de manera desacoplada.
 * 
 * **Uso:**
 * El `SessionManager` se utiliza principalmente para:
 * - Notificar cuando un interceptor de red detecta un error 401 (no autorizado)
 * - Observar eventos de expiración en ViewModels para navegar a la pantalla de login
 * - Gestionar el estado de sesión de forma centralizada
 * 
 * **Flujo típico:**
 * 1. Un interceptor de red detecta un error 401 y llama a `notifySessionExpired()`
 * 2. El `SharedViewModel` observa el flujo `sessionExpired` y actualiza su estado
 * 3. La UI reacciona al cambio de estado y navega a la pantalla de login
 * 4. Después del login exitoso, se llama a `resetSessionExpired()` para limpiar el estado
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class SessionManager {

    /**
     * Flujo mutable privado que emite eventos de expiración de sesión.
     * Se utiliza `MutableSharedFlow` para permitir múltiples colectores y no mantener estado.
     */
    private val _sessionExpired = MutableSharedFlow<Boolean>()

    /**
     * Flujo público de solo lectura que emite eventos de expiración de sesión.
     * 
     * **Valores:**
     * - `true`: La sesión ha expirado y el usuario debe iniciar sesión nuevamente
     * - `false`: La sesión está activa o se ha reseteado el estado de expiración
     * 
     * **Uso:**
     * Los componentes que necesitan reaccionar a eventos de expiración de sesión
     * deben observar este flujo usando `collect` o `collectAsStateWithLifecycle`.
     *
     * @see notifySessionExpired Para notificar que la sesión ha expirado
     * @see resetSessionExpired Para resetear el estado de expiración
     */
    val sessionExpired: SharedFlow<Boolean> = _sessionExpired.asSharedFlow()

    /**
     * Notifica que la sesión del usuario ha expirado.
     * 
     * **Cuándo usar:**
     * Este método debe ser llamado cuando se detecta que la sesión del usuario ha expirado,
     * típicamente cuando:
     * - Un interceptor de red recibe un error 401 (Unauthorized)
     * - Un token de acceso es inválido o ha expirado
     * - El servidor indica que la sesión no es válida
     *
     */
    suspend fun notifySessionExpired() {
        _sessionExpired.emit(true)
    }

    /**
     * Resetea el estado de expiración de sesión.
     * 
     * **Cuándo usar:**
     * Este método debe ser llamado después de que el usuario haya iniciado sesión exitosamente
     * o cuando se quiera limpiar el estado de expiración de sesión.
     * 
     * **Comportamiento:**
     * Emite `false` en el flujo `sessionExpired`, indicando que la sesión está activa
     * o que el estado de expiración ha sido limpiado.
     *
     */
    suspend fun resetSessionExpired() {
        _sessionExpired.emit(false)
    }

}

