package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class GameStatusResult(val gamestatuses: List<GameStatus>)
