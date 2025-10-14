package com.mx.liftechnology.registroeducativo.main.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Interfaz para proporcionar despachadores de corutinas, facilitando la inyección de dependencias y los tests.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface DispatcherProvider {
    /** Despachador principal para actualizaciones de UI. */
    val main: CoroutineDispatcher
    /** Despachador para operaciones de entrada/salida (I/O). */
    val io: CoroutineDispatcher
    /** Despachador para tareas intensivas de CPU. */
    val default: CoroutineDispatcher
    /** Despachador no confinado, para casos de uso específicos. */
    val unconfined: CoroutineDispatcher
}

/**
 * Implementación por defecto de [DispatcherProvider].
 *
 * @author Pelkidev
 * @version 1.0.0
 */
class DefaultDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
