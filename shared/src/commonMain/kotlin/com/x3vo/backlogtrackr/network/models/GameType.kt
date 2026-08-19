package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameType(
    val id: ULong,
    val type: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
