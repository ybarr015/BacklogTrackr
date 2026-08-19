package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class PopularityPrimitive(
    val id: ULong,
    val gameId: Int,
    val popularityType: PopularityType? = null,
    @Deprecated("Deprecated by proto definition")
    val popularitySource: PopularitySourcePopularitySourceEnum,
    val value: Double,
    val calculatedAt: Long? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String,
    val externalPopularitySource: ExternalGameSource? = null
)
