package dev.haqim.scrumdingerkmmlib.domain.usecase

import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.models.DailyScrum
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateDailyScrumUseCase: KoinComponent {
    private val repository by inject<ScrumdingerRepository>()

    suspend operator fun invoke(data: DailyScrum){
        return repository.updateDailyScrum(data)
    }
}