package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class AgeRatingContentDescription(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: AgeRatingContentDescriptionCategoryEnum,
    val description: String,
    val checksum: String
)
