package dev.haqim.scrumdingerkmmlib.domain.usecase

import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetAllDailyScrumUseCase: KoinComponent {
    private val repository by inject<ScrumdingerRepository>()

    operator fun invoke(): Flow<List<DailyScrum>> {
        return repository.getAllDailyScrum()
    }
}