package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class PlatformFamily(
    val id: ULong,
    val name: String,
    val slug: String,
    val checksum: String
)
