package com.x3vo.backlogtrackr.network

import com.x3vo.backlogtrackr.ui.BacklogApi
import org.koin.core.context.startKoin
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.module


val appModule = module{
    single<BacklogApi>{ BacklogApi() }
    single<BacklogAPIRepository> {
        BacklogAPIRepository(
            backlogApi = get()
        )
    }
}

fun initKoin(configuration: KoinConfiguration? = null) {
    startKoin {
        includes(configuration)
        modules(appModule)
    }
}