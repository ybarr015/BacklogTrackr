package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameLocalization(
    val id: ULong,
    val name: String,
    val cover: Cover? = null,
    val game: Game? = null,
    val region: Region? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
