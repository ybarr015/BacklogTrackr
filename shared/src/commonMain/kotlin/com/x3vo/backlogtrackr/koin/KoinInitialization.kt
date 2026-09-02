package com.x3vo.backlogtrackr.koin

import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.x3vo.backlogtrackr.auth.AuthRepository
import com.x3vo.backlogtrackr.network.auth.BackLogAuthAPIService
import com.x3vo.backlogtrackr.network.BacklogAPIRepository
import com.x3vo.backlogtrackr.room.BacklogTrackrDBRepository
import com.x3vo.backlogtrackr.room.BacklogTrackrDatabase
import com.x3vo.backlogtrackr.ui.BacklogApi
import com.x3vo.backlogtrackr.ui.settings.auth.AuthViewModel
import com.x3vo.backlogtrackr.ui.settings.gameprogressoptions.GameProgressSettingsViewModel
import com.x3vo.backlogtrackr.ui.game_search.add_game.GameSearchDetailViewModel
import com.x3vo.backlogtrackr.ui.game_search.search_games.GameSearchViewModel
import com.x3vo.backlogtrackr.ui.library_games.LibraryGamesViewModel
import com.x3vo.backlogtrackr.ui.settings.platforms.PlatformViewModel
import com.x3vo.backlogtrackr.ui.settings.stores.StoresSettingsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.includes
import org.koin.dsl.module


val appModule = module{

    //used for just making the auth request auth tokens handled in the main client
    single(qualifier = named("AuthClient")) {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
        }
    }

    //http client
    single<HttpClient> {

        val authRepo = get<AuthRepository>()

        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )

            }

            install(HttpRequestRetry) {
                maxRetries = 5
                retryIf { request, response ->
                    !response.status.isSuccess()
                }

                retryOnException(
                    maxRetries = 5,
                    retryOnTimeout = true

                )

                delayMillis { retry ->
                    retry*3000L
                }
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        authRepo.authToken.first()?.let { BearerTokens(it, "") }
                    }

                    refreshTokens {
                        authRepo.makeAuthRequest()
                        authRepo.authToken.first()?.let { BearerTokens(it, "") }

                    }
                }

            }

            install(HttpCache)
        }
    }


    //auth
    single { AuthRepository(dataStore = get(), backLogAuthAPIService = get()) }
    single { BackLogAuthAPIService(httpClient = get(named("AuthClient"))) }

    //api repository
    single<BacklogApi>{ BacklogApi(httpClient = get(), authRepository = get()) }
    single<BacklogAPIRepository> {
        BacklogAPIRepository(
            backlogApi = get()
        )
    }

    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    //room
    single<BacklogTrackrDatabase> {
        get<RoomDatabase.Builder<BacklogTrackrDatabase>>()
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single { get<BacklogTrackrDatabase>().getPlatformDao() }
    single { get<BacklogTrackrDatabase>().getPCStoreDAO() }
    single { get<BacklogTrackrDatabase>().getGameProgressOptionsDao() }
    single { get<BacklogTrackrDatabase>().getLibraryGameDao() }
    single { BacklogTrackrDBRepository(platformDao = get(), pcStoreDAO = get(), gameProgressOptionsDao = get(), libraryGameDao = get(), dataStore = get(), appScope = get()) }


    //view models
    viewModelOf(::AuthViewModel)
    viewModelOf(::GameSearchViewModel)
    viewModelOf(::GameSearchDetailViewModel)
    viewModelOf(::PlatformViewModel)
    viewModelOf(::StoresSettingsViewModel)
    viewModelOf(::GameProgressSettingsViewModel)
    viewModelOf(::LibraryGamesViewModel)
}

expect val platformModule: Module

fun initKoin(configuration: KoinConfiguration? = null) {
    startKoin {
        includes(configuration)
        modules(appModule, platformModule)
    }
}
