package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class GameContentSafetyRating(
    val id: ULong,
    val game: Game? = null,
    val contentSafetyRatingDimension: ContentSafetyRatingDimension? = null,
    val contentSafetyRating: ContentSafetyRating? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
