package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameTimeToBeat(
    val id: ULong,
    val gameId: Int,
    val hastily: Int,
    val normally: Int,
    val completely: Int,
    val count: Int,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
