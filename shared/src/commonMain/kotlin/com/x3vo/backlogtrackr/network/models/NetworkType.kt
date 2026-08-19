package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class NetworkType(
    val id: ULong,
    val name: String,
    val eventNetworks: List<EventNetwork>,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
