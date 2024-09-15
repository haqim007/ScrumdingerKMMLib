package dev.haqim.scrumdingerkmmlib.util

import dev.icerock.moko.resources.StringResource

expect class Strings {
    fun get(id: StringResource, args: List<Any>): String

    fun get(id: StringResource): String
}
