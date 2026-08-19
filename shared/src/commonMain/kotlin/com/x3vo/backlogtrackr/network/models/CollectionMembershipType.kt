package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class CollectionMembershipType(
    val id: ULong,
    val name: String,
    val description: String,
    val allowedCollectionType: CollectionType? = null,
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val checksum: String
)
