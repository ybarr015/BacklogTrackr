package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameStatus(
    val id: ULong,
    val status: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
