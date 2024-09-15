package dev.haqim.scrumdingerkmmlib.data.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import dev.haqim.scrumdingerkmmlib.domain.models.History
import dev.haqim.scrumdingerkmmlib.domain.models.Theme
import dev.haqim.scrumdingerkmmlib.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Database(
    databaseDriverFactory: DatabaseDriverFactory,
    private val dispatcherProvider: DispatcherProvider
) {
    private val database = MyScrumdingerDB(databaseDriverFactory.createDriver())

    internal fun getAllDailyScrum(): Flow<List<DailyScrum>>{
        return database.dailyScrumQueries
            .selectAllDailyScrums(::mapDailyScrumSelecting)
            .asFlow()
            .mapToList(dispatcherProvider.default)
    }

    // Define a key for grouping
    private data class GroupingKey(
        val scrum_id: Long,
        val title: String,
        val attendees: String,
        val lengthInMinutes: Long,
        val theme: String,
        val dateTimeUTC: String?
    )

    internal fun getDailyScrum(id: Long): Flow<DailyScrum?>{
        return database.dailyScrumQueries
            .selectDailyScrumsWithHistories(id)
            .asFlow()
            .mapToList(dispatcherProvider.default)
            .map {
                val data = it
                    .groupBy { it.scrum_id }
                    .map { (scrumId, scrumData) ->
                        val firstEntry = scrumData.first()

                        val histories = scrumData
                            .filter {
                                it.history_id != null
                            }
                            .map { entry ->
                                History(
                                    id = entry.history_id!!,
                                    dateTimeUTC = entry.datetime_utc ?: "",
                                    attendees = entry.attendee.split(",")
                                        .filter { it.isNotBlank() }
                                        .mapIndexed { index, name ->
                                            DailyScrum.Attendee(
                                                id = index.toLong(),
                                                name = name
                                            )
                                        },
                                    transcript = entry.transcript
                                )
                            }
                            .sortedByDescending {
                                it.dateTimeUTC
                            }

                        DailyScrum(
                            id = scrumId,
                            title = firstEntry.title,
                            lengthInMinutes = firstEntry.lengthInMinutes.toInt(),
                            theme = Theme.fromColorName(firstEntry.theme) ?: Theme.yellow,
                            attendees = firstEntry.attendee.split(",")
                                .filter { it.isNotBlank() }
                                .mapIndexed { index, it ->
                                    DailyScrum.Attendee(
                                        id = index.toLong(),
                                        name = it
                                    )
                                },
                            history = histories
                        )
                    }

                return@map if (data.isNotEmpty()) data[0] else null
            }
    }

    private fun mapDailyScrumSelecting(
        id: Long,
        title: String,
        lengthInMinutes: Long,
        theme: String,
        attendees: String
    ): DailyScrum {
        return DailyScrum(
            id = id,
            title = title,
            lengthInMinutes = lengthInMinutes.toInt(),
            theme = Theme.fromColorName(theme) ?: Theme.yellow,
            attendees = attendees.split(",")
                .filter { it.isNotBlank() }
                .mapIndexed { index, it ->
                    DailyScrum.Attendee(
                        id = index.toLong(),
                        name = it
                    )
                }
        )
    }

    internal fun insertDailyScrum(
        title: String,
        lengthInMinutes: Long,
        themeString: String,
        attendees: String
    ){
        database.dailyScrumQueries.insertDailyScrum(
            title = title,
            lengthInMinutes = lengthInMinutes,
            theme = themeString,
            attendee = attendees
        )
    }

    internal fun updateDailyScrum(data: DailyScrum){
        database.dailyScrumQueries.updateDailyScrum(
            title = data.title,
            lengthInMinutes = data.lengthInMinutes.toLong(),
            theme = data.theme.colorName,
            attendee = data.attendees.joinToString(separator = ","){it.name},
            id = data.id
        )
    }

    internal fun getDailyScrumHistories(scrumId: Long): Flow<List<History>>{
        return database.historyQueries
            .selectHistoryByScrumId(scrumId, ::mapHistoryEntries)
            .asFlow()
            .mapToList(dispatcherProvider.default)
    }

    private fun mapHistoryEntries(
        id: Long,
        transcript: String,
        scrumId: Long,
        attendees: String,
        dateTimeUTC: String
    ): History {
        return History(
            id = id,
            transcript = transcript,
            attendees = attendees.split(",")
            .filter { it.isNotBlank() }
            .mapIndexed { index, it ->
                DailyScrum.Attendee(
                    id = index.toLong(),
                    name = it
                )
            },
            dateTimeUTC = dateTimeUTC
        )
    }

    internal fun insertDailyScrumHistory(
        transcript: String,
        attendees: String,
        dateTimeUTC: String,
        scrumId: Long
    ){
        database.historyQueries.insertHistory(
            transcript = transcript,
            attendees = attendees,
            datetime_utc = dateTimeUTC,
            scrum_id = scrumId
        )
    }
}
