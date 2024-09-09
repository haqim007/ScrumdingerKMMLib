package dev.haqim.scrumdingerkmmlib.util

import kotlinx.datetime.Clock


fun generateUniqueId(salt: Long = 0L): Long {
    return Clock.System.now().toEpochMilliseconds()+salt
}

fun generateUniqueId(): Long {
    return Clock.System.now().toEpochMilliseconds()
}