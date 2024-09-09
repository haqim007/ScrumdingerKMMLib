package dev.haqim.scrumdingerkmmlib.domain.usecase

import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import dev.haqim.scrumdingerkmmlib.domain.models.Theme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddDailyScrumUseCase: KoinComponent {
    private val repository by inject<ScrumdingerRepository>()

    suspend operator fun invoke(
        title: String,
        lengthInMinutes: Int,
        theme: Theme,
        attendees: List<DailyScrum.Attendee>
    ){
        return repository.insertDailyScrum(
            title, lengthInMinutes, theme, attendees
        )
    }
}