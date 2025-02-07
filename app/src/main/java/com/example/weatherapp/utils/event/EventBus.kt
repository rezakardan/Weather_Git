package com.example.weatherapp.utils.event

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

object EventBus {
    private val events = MutableSharedFlow<Any>()
    val usableEvent = events.asSharedFlow()

    suspend fun publish(event: Any) {
        events.emit(event)
    }

    suspend inline fun <reified T> subscribe(crossinline onEvents: (T) -> Unit) {
        usableEvent.filterIsInstance<T>().collectLatest {
            coroutineContext.ensureActive()
            onEvents(it)
        }
    }
}