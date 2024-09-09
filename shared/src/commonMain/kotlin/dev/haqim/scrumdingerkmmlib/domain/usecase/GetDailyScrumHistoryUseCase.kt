package dev.haqim.scrumdingerkmmlib.domain.usecase

import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import dev.haqim.scrumdingerkmmlib.domain.models.History
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetDailyScrumHistoryUseCase: KoinComponent {
    private val repository by inject<ScrumdingerRepository>()

    operator fun invoke(scrumId: Long): Flow<List<History>>{
        return repository.getDailyScrumHistory(scrumId)
    }
}