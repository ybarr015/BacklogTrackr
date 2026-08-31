package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class GameReleaseFormat(
    val id: ULong,
    val format: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
