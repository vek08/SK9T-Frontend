package com.k9t.scoreknight.auth.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventManager {
    private val _authRequiredFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val authRequiredFlow = _authRequiredFlow.asSharedFlow()

    fun triggerAuthRequired() {
        _authRequiredFlow.tryEmit(Unit)
    }
}
