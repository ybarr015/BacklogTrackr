package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class GameVideo(
    val id: ULong,
    val game: Game? = null,
    val name: String,
    val videoId: String,
    val checksum: String
)
