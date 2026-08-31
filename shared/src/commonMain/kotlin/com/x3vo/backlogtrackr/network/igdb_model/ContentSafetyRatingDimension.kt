package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class ContentSafetyRatingDimension(
    val id: ULong,
    val contentSafetyRating: ContentSafetyRating? = null,
    val value: Int,
    val description: String,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
