package com.x3vo.backlogtrackr.ui

import com.x3vo.backlogtrackr.SharedBuildConfig
import com.x3vo.backlogtrackr.auth.AuthRepository
import com.x3vo.backlogtrackr.network.models.Game
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.flow.first

class BacklogApi(private val httpClient: HttpClient, private val authRepository: AuthRepository) {

    private companion object {
        const val IGDB_ENDPOINT = "https://api.igdb.com/v4/"

    }


    /*
    TODO
    make requests with manual pagination

     */

    suspend fun gameEndpoint(gameName: String): List<Game> {
        val accessToken = authRepository.authToken.first()
        println("BacklogApi: Using access token: $accessToken")
        val result = httpClient.post(IGDB_ENDPOINT) {
            url{
                appendPathSegments("games")
            }
            headers{
                append("Client-ID", SharedBuildConfig.CLIENT_ID)
            }

            setBody("search \"$gameName\";where game_type = 0;fields name, summary, id, cover.id, cover.width, cover.height, cover.url ;limit 10;")
        }.body() as List<Game>
        println("BacklogApi: Received ${result.size} games")
        return result
    }
}
