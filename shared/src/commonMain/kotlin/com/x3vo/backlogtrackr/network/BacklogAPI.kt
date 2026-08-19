package com.x3vo.backlogtrackr.ui

import com.x3vo.backlogtrackr.network.AuthResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post


interface  ApiService {
    suspend fun getAuthData(): AuthResponse
}

class BacklogApi(private val httpClient: HttpClient): ApiService {

    private companion object {
        const val TOKEN_URL = "https://id.twitch.tv/oauth2/token"
    }


    /*
    TODO

    generate responses from the api from protobuf file to json files
    add in auth plugin and the retry one into ktor
    create ui objects
    add in safe api call to handle certain errors
    make requests with manual pagination

     */


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