package com.x3vo.backlogtrackr.network.models

import kotlinx.serialization.Serializable




@Serializable
data class AgeRatingContentDescriptionV2(
    val id: ULong,
    val description: String,
    val organization: AgeRatingOrganization? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String,
    val descriptionType: AgeRatingContentDescriptionType? = null
)
