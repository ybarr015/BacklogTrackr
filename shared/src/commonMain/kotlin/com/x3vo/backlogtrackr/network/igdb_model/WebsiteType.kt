package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class WebsiteType(
    val id: ULong,
    val type: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
