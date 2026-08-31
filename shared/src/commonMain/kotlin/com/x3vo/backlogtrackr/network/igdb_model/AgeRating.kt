package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable

@Serializable
data class AgeRating(
    val id: ULong,
    @Deprecated("Deprecated by proto definition")
    val category: AgeRatingCategoryEnum,
    @Deprecated("Deprecated by proto definition")
    val contentDescriptions: List<AgeRatingContentDescription>,
    @Deprecated("Deprecated by proto definition")
    val rating: AgeRatingRatingEnum,
    val ratingCoverUrl: String,
    val synopsis: String,
    val checksum: String,
    val organization: AgeRatingOrganization? = null,
    val ratingCategory: AgeRatingCategory? = null,
    val ratingContentDescriptions: List<AgeRatingContentDescriptionV2>
)
