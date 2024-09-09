package dev.haqim.scrumdingerkmmlib.domain.usecase

import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import dev.haqim.scrumdingerkmmlib.domain.models.History
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddDailyScrumHistoryUseCase: KoinComponent {
    private val repository by inject<ScrumdingerRepository>()

    suspend operator fun invoke(
        transcript: String,
        attendees: List<DailyScrum.Attendee>,
        dateTimeUTC: String,
        scrumId: Long
    ){
        return repository.insertHistory(
            transcript, attendees, dateTimeUTC, scrumId
        )
    }
}