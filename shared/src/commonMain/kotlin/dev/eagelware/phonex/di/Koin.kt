package dev.eagelware.phonex.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

val dataModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = { androidContext().preferencesDataStoreFile("settings") } //
        )
    }
    single{ AccountRepository(get()) }
    single<AccountStateHolder> { AccountStateHolderImpl(get()) }

//    single<AqademicData>{
//        AqademicDatum(androidContext())
//    }

}
fun initKoin(config: KoinAppDeclaration? = null){
    startKoin{
        includes(config)
    }
}