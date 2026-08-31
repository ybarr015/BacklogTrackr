package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class PlatformVersionReleaseDate(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: DateFormatChangeDateCategoryEnum,
    val createdAt: Long? = null,
    val date: Long? = null,
    val human: String,
    val m: Int,
    val platformVersion: PlatformVersion? = null,
    @Deprecated("Deprecated by proto definition")
    val region: RegionRegionEnum,
    val updatedAt: Long? = null,
    val y: Int,
    val checksum: String,
    val dateFormat: DateFormat? = null,
    val releaseRegion: ReleaseDateRegion? = null
)
