package com.mx.liftechnology.core.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SessionManager {

    private val _sessionExpired = MutableSharedFlow<Boolean>()
    val sessionExpired = _sessionExpired.asSharedFlow()

    suspend fun notifySessionExpired() {
        _sessionExpired.emit(true)
    }

    suspend fun resetSessionExpired() {
        _sessionExpired.emit(false)
    }

}