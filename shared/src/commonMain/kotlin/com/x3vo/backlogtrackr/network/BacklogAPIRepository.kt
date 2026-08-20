package com.x3vo.backlogtrackr.network

import com.x3vo.backlogtrackr.network.models.Game
import com.x3vo.backlogtrackr.network.utils.Resource
import com.x3vo.backlogtrackr.network.utils.safeApiCall
import com.x3vo.backlogtrackr.ui.BacklogApi

class BacklogAPIRepository (private val backlogApi: BacklogApi) {
    suspend fun gameSearch(gameName: String): Resource<List<Game>> {
        return safeApiCall {
            backlogApi.gameEndpoint(gameName)
        }
    }
}