package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class Theme(
    val id: ULong,
    val createdAt: Long? = null,
    val name: String,
    val slug: String,
    val updatedAt: Long? = null,
    val url: String,
    val checksum: String
)
