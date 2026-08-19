package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class ArtworkType(
    val id: ULong,
    val slug: String,
    val name: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
