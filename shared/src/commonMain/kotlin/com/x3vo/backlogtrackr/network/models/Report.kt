package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class Report(
    val id: ULong,
    val sourceItemId: Int,
    val targetItemId: Int,
    val reportType: ReportType? = null,
    val entityType: EntityType? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
