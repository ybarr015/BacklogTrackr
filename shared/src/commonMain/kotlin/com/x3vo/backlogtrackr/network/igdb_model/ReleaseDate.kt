package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class ReleaseDate(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: DateFormatChangeDateCategoryEnum,
    val createdAt: Long? = null,
    val date: Long? = null,
    val game: Game? = null,
    val human: String,
    val m: Int,
    val platform: Platform? = null,
    @Deprecated("Deprecated by proto definition")
    val region: RegionRegionEnum,
    val updatedAt: Long? = null,
    val y: Int,
    val checksum: String,
    val status: ReleaseDateStatus? = null,
    val dateFormat: DateFormat? = null,
    val releaseRegion: ReleaseDateRegion? = null,
    val d: Int
)
