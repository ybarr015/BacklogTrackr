package com.x3vo.backlogtrackr.network.igdb_model

import kotlinx.serialization.Serializable




@Serializable
data class AgeRatingCategory(
    val id: ULong,
    val rating: String,
    val organization: AgeRatingOrganization? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val checksum: String
)
