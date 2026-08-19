package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class Region(
    val id: ULong,
    val name: String,
    val category: String,
    val identifier: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
