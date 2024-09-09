package dev.haqim.scrumdingerkmmlib.domain.models

import dev.haqim.scrumdingerkmmlib.util.generateUniqueId
import dev.haqim.scrumdingerkmmlib.util.getDateTimeNow
import dev.haqim.scrumdingerkmmlib.util.toISO8601String

interface IHistory{
    val id: Long
    val dateTimeUTC: String
    val attendees: List<DailyScrum.Attendee>
    val transcript: String?
}

data class History(
    override val id: Long = generateUniqueId(),
    override val dateTimeUTC: String = getDateTimeNow().toISO8601String(),
    override val attendees: List<DailyScrum.Attendee>,
    override val transcript: String? = null
): IHistory