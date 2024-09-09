package dev.haqim.scrumdingerkmmlib.util

import kotlinx.coroutines.CoroutineDispatcher

// commonMain
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
