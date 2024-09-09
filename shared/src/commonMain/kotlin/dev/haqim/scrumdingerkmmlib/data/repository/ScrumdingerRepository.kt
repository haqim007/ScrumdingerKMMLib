package dev.haqim.scrumdingerkmmlib.data.repository

import dev.haqim.scrumdingerkmmlib.data.cache.Database
import dev.haqim.scrumdingerkmmlib.data.cache.DatabaseDriverFactory
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import dev.haqim.scrumdingerkmmlib.domain.models.History
import dev.haqim.scrumdingerkmmlib.domain.models.Theme
import dev.haqim.scrumdingerkmmlib.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow

internal class ScrumdingerRepository(
    databaseDriverFactory: DatabaseDriverFactory,
    dispatcherProvider: DispatcherProvider
) {
    private val database = Database(databaseDriverFactory, dispatcherProvider)

    @Throws(Exception::class)
    fun getAllDailyScrum(): Flow<List<DailyScrum>>{
        return database.getAllDailyScrum()
    }

    @Throws(Exception::class)
    fun getDailyScrum(scrumId: Long): Flow<DailyScrum?>{
        return database.getDailyScrum(scrumId)
    }

    @Throws(Exception::class)
    fun insertDailyScrum(
        title: String,
        lengthInMinutes: Int,
        theme: Theme,
        attendees: List<DailyScrum.Attendee>
    ){
        database.insertDailyScrum(
            title = title,
            lengthInMinutes = lengthInMinutes.toLong(),
            themeString = theme.colorName,
            attendees = attendees.joinToString(separator = ",") { it.name }
        )
    }

    @Throws(Exception::class)
    suspend fun updateDailyScrum(data: DailyScrum){
        database.updateDailyScrum(data)
    }

    @Throws(Exception::class)
    fun getDailyScrumHistory(scrumId: Long): Flow<List<History>>{
        return database.getDailyScrumHistories(scrumId)
    }

    @Throws(Exception::class)
    suspend fun insertHistory(
        transcript: String,
        attendees: List<DailyScrum.Attendee>,
        dateTimeUTC: String,
        scrumId: Long
    ){
        return database.insertDailyScrumHistory(
            transcript = transcript,
            attendees = attendees.joinToString(separator = ",") { it.name },
            dateTimeUTC, scrumId
        )
    }
}
