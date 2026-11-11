package dev.eagelware.phonex.di

import dev.eagelware.phonex.data.repo.UserSettingsRepository
import dev.eagelware.phonex.platformDataStoreModule
import dev.eagelware.phonex.viewmodel.UserSettingsViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.koin.dsl.module
import org.koin.plugin.module.dsl.*

val dataModule = module {
    single<UserSettingsRepository>()
    viewModel<UserSettingsViewModel>()
}

val appModules = module {
    includes(platformDataStoreModule(), dataModule)
}
fun initKoin(config: KoinAppDeclaration? = null){
    startKoin{
        includes(config)
        modules(appModules)
    }
}