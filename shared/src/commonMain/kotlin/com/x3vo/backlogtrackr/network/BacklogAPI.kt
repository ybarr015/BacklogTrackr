package com.x3vo.backlogtrackr.ui

import com.x3vo.backlogtrackr.network.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


interface  ApiService {
    suspend fun getAuthData(): AuthResponse
}

class BacklogApi: ApiService {

    private companion object {
        const val TOKEN_URL = "https://id.twitch.tv/oauth2/token"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )

        }
    }


    override suspend fun getAuthData(): AuthResponse {
        return httpClient.post(TOKEN_URL) {
            url {
                parameters.append("client_id", "")
                parameters.append("client_secret", "")
                parameters.append("grant_type", "client_credentials")
            }
        }.body() as AuthResponse
    }
}