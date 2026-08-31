package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class Platform(
    val id: ULong,
    val abbreviation: String,
    val alternativeName: String,
    @Deprecated("Deprecated by proto definition")
    val category: PlatformCategoryEnum,
    val createdAt: Long? = null,
    val generation: Int,
    val name: String,
    val platformLogo: PlatformLogo? = null,
    val platformFamily: PlatformFamily? = null,
    val slug: String,
    val summary: String,
    val updatedAt: Long? = null,
    val url: String,
    val versions: List<PlatformVersion>,
    val websites: List<PlatformWebsite>,
    val checksum: String,
    val platformType: PlatformType? = null
)
