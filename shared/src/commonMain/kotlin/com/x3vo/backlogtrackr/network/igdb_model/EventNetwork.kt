package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class EventNetwork(
    val id: ULong,
    val event: Event? = null,
    val url: String,
    val networkType: NetworkType? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
