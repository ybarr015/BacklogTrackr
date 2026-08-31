package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class ReportType(
    val id: ULong,
    val name: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
