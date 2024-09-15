package dev.haqim.scrumdingerkmmlib.domain.models

import dev.haqim.scrumdingerkmmlib.domain.models.Theme.orange
import dev.haqim.scrumdingerkmmlib.domain.models.Theme.poppy
import dev.haqim.scrumdingerkmmlib.domain.models.Theme.sky
import dev.haqim.scrumdingerkmmlib.util.generateUniqueId


data class DailyScrum(
    val id: Long = generateUniqueId(),
    var title: String,
    var attendees: List<Attendee>,
    var lengthInMinutes: Int,
    var theme: Theme,
    var history: List<History> = emptyList()
) {

    var themeString: String
        get() = theme.colorName
        set(value) {
            theme = Theme.fromColorName(value) ?: Theme.yellow
        }

    val accentColor: String
        get (){
            return theme.accentColor
        }

    var lengthInMinutesAsDouble: Double
        get() = lengthInMinutes.toDouble()
        set(value) {
            lengthInMinutes = value.toInt()
        }

    data class Attendee(
        val id: Long = generateUniqueId(),
        var name: String
    )

    // Companion object for static-like functionality in Kotlin
    companion object {

        // Extension function for List<Attendee>
        fun getSpeakers(attendees: List<DailyScrum.Attendee>): List<Speaker> {
            return if (attendees.isEmpty()) {
                listOf(Speaker(id = 1, name = "Speaker 1", isCompleted = false))
            } else {
                attendees.mapIndexed { index, attendee ->
                    Speaker(
                        id = generateUniqueId(index.toLong()),
                        name = attendee.name,
                        isCompleted = false)

                }
            }
        }

        val sampleData: List<DailyScrum> = listOf(
            DailyScrum(
                id = generateUniqueId(1),
                title = "Design",
                attendees = listOf("Cathy", "Daisy", "Simon", "Jonathan").mapIndexed { index, it ->
                    Attendee(id = generateUniqueId(index.toLong()), name = it)
                },
                lengthInMinutes = 10,
                theme = Theme.yellow
            ),
            DailyScrum(
                id = generateUniqueId(2),
                title = "App Dev",
                attendees = listOf("Katie", "Gray", "Euna", "Luis", "Darla").mapIndexed { index, it ->
                    Attendee(id = generateUniqueId(index.toLong()), name = it)
                },
                lengthInMinutes = 5,
                theme = orange
            ),
            DailyScrum(
                id = generateUniqueId(3),
                title = "Web Dev",
                attendees = listOf("Chella", "Chris", "Christina", "Eden", "Karla", "Lindsey", "Aga", "Chad", "Jenn", "Sarah").mapIndexed { index, it ->
                    Attendee(id = generateUniqueId(index.toLong()), name = it)
                },
                lengthInMinutes = 5,
                theme = poppy
            )
        )

        val emptyScrum: DailyScrum
            get() = DailyScrum(
                id = generateUniqueId(1L),
                title = "",
                attendees = listOf(),
                lengthInMinutes = 5,
                theme = sky
            )
    }
}


// Define the data class
data class Speaker(
    val name: String,
    var isCompleted: Boolean,
    val id: Long = generateUniqueId()
)
