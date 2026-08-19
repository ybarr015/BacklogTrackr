package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class CollectionRelation(
    val id: ULong,
    val childCollection: CollectionDto? = null,
    val parentCollection: CollectionDto? = null,
    val type: CollectionRelationType? = null,
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val checksum: String
)
