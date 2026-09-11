package com.x3vo.backlogtrackr.ui

import com.x3vo.backlogtrackr.SharedBuildConfig
import com.x3vo.backlogtrackr.auth.AuthRepository
import com.x3vo.backlogtrackr.network.igdb_model.Game
import com.x3vo.backlogtrackr.network.igdb_model.GameTimeToBeat
import com.x3vo.backlogtrackr.network.igdb_model.Search
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments

class BacklogApi(private val httpClient: HttpClient, private val authRepository: AuthRepository) {

    private companion object {
        const val IGDB_ENDPOINT = "https://api.igdb.com/v4/"

    }


    /*
    TODO
    make requests with manual pagination

     */

    suspend fun gameSearchByName(gameName: String): List<Game> {
        val result = httpClient.post(IGDB_ENDPOINT) {
            url{
                appendPathSegments("search")
            }
            headers{
                append("Client-ID", SharedBuildConfig.CLIENT_ID)
            }

            val searchQuery = """
                search "${gameName}";
                fields game.name, game.summary, game.id, game.cover.id, game.cover.width, game.cover.height, game.cover.url ;
                where game != null & game.game_type = 0;
                limit 10;
                """.trimIndent()
            setBody(searchQuery)
        }.body() as List<Search>
        println("BacklogApi: Received ${result.size} games")
        return result.mapNotNull { it.game }
    }

    suspend fun gameSearchById(gameId: String): List<Game> {
        val result = httpClient.post(IGDB_ENDPOINT) {
            url{
                appendPathSegments("games")
            }
            headers{
                append("Client-ID", SharedBuildConfig.CLIENT_ID)
            }

            setBody("where id = $gameId;fields name, summary, id, cover.id, cover.width, cover.height, cover.url;")
        }.body() as List<Game>
        println("BacklogApi: Received ${result.size} games")
        return result
    }

    suspend fun getTimeToBeat(gameId: String): List<GameTimeToBeat> {
        val result = httpClient.post(IGDB_ENDPOINT) {
            url {
                appendPathSegments("game_time_to_beats")
            }
            headers {
                append("Client-ID", SharedBuildConfig.CLIENT_ID)
            }
            setBody("where game_id = $gameId;fields normally;")
        }.body() as List<GameTimeToBeat>
        println("BacklogApi: Received time to beat for $gameId")
        return result
    }
}
