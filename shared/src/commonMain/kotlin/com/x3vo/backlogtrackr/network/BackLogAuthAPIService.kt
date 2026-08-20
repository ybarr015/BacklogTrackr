package com.x3vo.backlogtrackr.network

import com.x3vo.backlogtrackr.SharedBuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post

class BackLogAuthAPIService(private val httpClient: HttpClient) {

    private companion object {
        const val IGDB_TOKEN_URL = "https://id.twitch.tv/oauth2/token"
    }

    suspend fun getAuthData(): AuthResponse {
        return httpClient.post(IGDB_TOKEN_URL) {
            url {
                parameters.append("client_id", SharedBuildConfig.CLIENT_ID)
                parameters.append("client_secret", SharedBuildConfig.CLIENT_SECRET)
                parameters.append("grant_type", "client_credentials")
            }
        }.body() as AuthResponse
    }
}