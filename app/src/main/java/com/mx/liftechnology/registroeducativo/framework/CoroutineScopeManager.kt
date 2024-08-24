package com.mx.liftechnology.registroeducativo.framework

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/** CoroutineScopeManager - Personalized
 * @author pelkidev
 * @since 1.0.0
 * */
class CoroutineScopeManager {
    private var job = Job()
    var scopeMainThread: CoroutineScope = CoroutineScope(job + Dispatchers.Main)
    var scopeIO: CoroutineScope = CoroutineScope(job + Dispatchers.IO)
    var scopeDefault: CoroutineScope = CoroutineScope(job + Dispatchers.Default)
    var scopeUnconfined: CoroutineScope = CoroutineScope(job + Dispatchers.Unconfined)

    /*fun cancelGeneric() {
        job.cancel()
        job = Job()
        scopeMainThread = CoroutineScope(job + Dispatchers.Main)
        scopeIO = CoroutineScope(job + Dispatchers.IO)
        scopeDefault = CoroutineScope(job + Dispatchers.Default)
        scopeUnconfined = CoroutineScope(job + Dispatchers.Unconfined)
    }*/
}
 