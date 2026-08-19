package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class PopularityType(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val popularitySource: PopularitySourcePopularitySourceEnum,
    val name: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String,
    val externalPopularitySource: ExternalGameSource? = null
)
