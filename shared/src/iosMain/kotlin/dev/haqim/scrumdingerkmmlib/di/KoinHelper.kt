package dev.haqim.scrumdingerkmmlib.di

import dev.haqim.scrumdingerkmmlib.data.cache.IOSDatabaseDriverFactory
import dev.haqim.scrumdingerkmmlib.data.repository.ScrumdingerRepository
import dev.haqim.scrumdingerkmmlib.domain.usecase.AddDailyScrumHistoryUseCase
import dev.haqim.scrumdingerkmmlib.domain.usecase.AddDailyScrumUseCase
import dev.haqim.scrumdingerkmmlib.domain.usecase.GetAllDailyScrumUseCase
import dev.haqim.scrumdingerkmmlib.domain.usecase.GetDailyScrumHistoryUseCase
import dev.haqim.scrumdingerkmmlib.domain.usecase.GetDailyScrumUseCase
import dev.haqim.scrumdingerkmmlib.domain.usecase.UpdateDailyScrumUseCase
import dev.haqim.scrumdingerkmmlib.util.DispatcherProvider
import dev.haqim.scrumdingerkmmlib.util.DispatcherProviderImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinHelper {
    fun initKoin() {
        startKoin {
            modules(module {
                single<ScrumdingerRepository> {
                    ScrumdingerRepository(
                        databaseDriverFactory = IOSDatabaseDriverFactory(),
                        dispatcherProvider = get()
                    )
                }
                single {
                    GetAllDailyScrumUseCase()
                }
                single {
                    GetDailyScrumUseCase()
                }
                single {
                    AddDailyScrumUseCase()
                }
                single {
                    UpdateDailyScrumUseCase()
                }
                single {
                    GetDailyScrumHistoryUseCase()
                }
                single {
                    AddDailyScrumHistoryUseCase()
                }
                single<DispatcherProvider> {
                    DispatcherProviderImpl()
                }
            })
        }
    }
}