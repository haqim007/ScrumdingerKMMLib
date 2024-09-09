package dev.haqim.scrumdingerkmmlib.domain.usecase

import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetDailyScrumUseCase: KoinComponent {
    private val repository by inject<ScrumdingerRepository>()

    operator fun invoke(scrumId: Long): Flow<DailyScrum?> {
        return repository.getDailyScrum(scrumId)
    }
}