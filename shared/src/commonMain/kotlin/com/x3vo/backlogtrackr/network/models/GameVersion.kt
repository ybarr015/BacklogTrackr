package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameVersion(
    val id: ULong,
    val createdAt: Long? = null,
    val features: List<GameVersionFeature>,
    val game: Game? = null,
    val games: List<Game>,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String
)
