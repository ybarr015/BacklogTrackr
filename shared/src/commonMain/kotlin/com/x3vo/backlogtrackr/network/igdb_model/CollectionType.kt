package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class CollectionType(
    val id: ULong,
    val name: String,
    val description: String,
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val checksum: String
)
