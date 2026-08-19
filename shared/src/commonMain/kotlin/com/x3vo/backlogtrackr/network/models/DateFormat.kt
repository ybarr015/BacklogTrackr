package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class DateFormat(
    val id: ULong,
    val format: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
