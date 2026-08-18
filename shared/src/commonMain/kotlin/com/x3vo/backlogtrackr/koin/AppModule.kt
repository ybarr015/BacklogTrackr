package com.x3vo.backlogtrackr.network

import com.x3vo.backlogtrackr.ui.BacklogApi
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.module


val appModule = module{

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )

            }
        }
    }

    single<BacklogApi>{ BacklogApi(httpClient = get()) }
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