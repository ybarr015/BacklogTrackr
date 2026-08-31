package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class CollectionMembership(
    val id: ULong,
    val game: Game? = null,
    val collection: CollectionDto? = null,
    val type: CollectionMembershipType? = null,
    val updatedAt: Long? = null,
    val createdAt: Long? = null,
    val checksum: String
)
