package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class EventLogo(
    val id: ULong,
    val event: Event? = null,
    val alphaChannel: Boolean,
    val animated: Boolean,
    val height: Int,
    val imageId: String,
    val url: String,
    val width: Int,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
