package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class CollectionDto(
    val id: ULong,
    val createdAt: Long? = null,
    val games: List<Game>,
    val name: String,
    val slug: String,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String,
    val type: CollectionType? = null,
    val asParentRelations: List<CollectionRelation>,
    val asChildRelations: List<CollectionRelation>
)
