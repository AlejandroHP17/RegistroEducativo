package com.mx.liftechnology.registroeducativo.main.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * An interface for providing Coroutine dispatchers.
 *
 * @author Pelkidev
 * @version 1.0.0
 */
interface DispatcherProvider {
    /** The main dispatcher. */
    val main: CoroutineDispatcher
    /** The I/O dispatcher. */
    val io: CoroutineDispatcher
    /** The default dispatcher. */
    val default: CoroutineDispatcher
    /** The unconfined dispatcher. */
    val unconfined: CoroutineDispatcher
}

/**
 * The default implementation of [DispatcherProvider].
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
