package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class CollectionRelationType(
    val id: ULong,
    val name: String,
    val description: String,
    val allowedChildType: CollectionType? = null,
    val allowedParentType: CollectionType? = null,
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val checksum: String
)
