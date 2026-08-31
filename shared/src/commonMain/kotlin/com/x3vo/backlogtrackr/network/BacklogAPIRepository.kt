package com.x3vo.backlogtrackr.network

import com.x3vo.backlogtrackr.network.igdb_model.Game
import com.x3vo.backlogtrackr.network.igdb_model.GameTimeToBeat
import com.x3vo.backlogtrackr.network.utils.Resource
import com.x3vo.backlogtrackr.network.utils.safeApiCall
import com.x3vo.backlogtrackr.ui.BacklogApi

class BacklogAPIRepository (private val backlogApi: BacklogApi) {
    suspend fun gameSearchByName(gameName: String): Resource<List<Game>> {
        return safeApiCall {
            backlogApi.gameSearchByName(gameName).map { it.formatCoverURL() }
        }
    }

    suspend fun gameSearchById(gameId: String): Resource<List<Game>> {
        return safeApiCall {
            backlogApi.gameSearchById(gameId).map { it.formatCoverURL() }
        }
    }

    suspend fun getTimeToBeat(gameId: String): Resource<List<GameTimeToBeat>> {
        return safeApiCall {
            backlogApi.getTimeToBeat(gameId)
        }
    }

    private fun Game.formatCoverURL(): Game {
        val rawCoverURL = this.cover?.url ?: return this
        val formattedCoverURL = if (rawCoverURL.isNotEmpty()) {
            "https:${rawCoverURL.replace("t_thumb", "t_cover_big")}"
        } else {
            ""
        }
        return this.copy(cover = this.cover.copy(url = formattedCoverURL))
    }
}
